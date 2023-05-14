package model;

import controller.GameMenuController;

import java.util.ArrayList;
import java.util.Objects;

public class Troop implements Cloneable{
    public static ArrayList<Troop> Troops = new ArrayList<>();
    String troopName;
    int price;
    ArrayList<Item> equipment;

    int attackPower, defencePower, speed, attackRange;
    int HP;

    boolean mercenary;
    boolean patrolling;
    int patrolIndex = 0;
    PatrolRoute patrolRoute;

    public enum TroopType{Melee, Ranged, Engineer};
    TroopType troopType;

    int x, y;
    int destinationX, destinationY;
    Government owner;
    Troop targetEnemy;
    int targetX, targetY;
    public enum State{Standing, Defensive, Offensive};
    State state;

    public Troop(String troopName, int price, ArrayList<Item> equipment, int attackPower, int defencePower, int speed, int attackRange, int HP, boolean mercenary, TroopType troopType) {
        this.troopName = troopName;
        this.price = price;
        this.equipment = equipment;
        this.attackPower = attackPower;
        this.defencePower = defencePower;
        this.speed = speed;
        this.attackRange = attackRange;
        this.HP = HP;
        this.troopType = troopType;
        this.mercenary = mercenary;
        owner = null;

        destinationX = -1;
        destinationY = -1;
        targetX = -1;
        targetY = -1;

        patrolling = false;
        patrolRoute = null;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Government getOwner() {
        return owner;
    }

    public String getTroopName() {
        return troopName;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<Item> getEquipment() {
        return equipment;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefencePower() {
        return defencePower;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isMercenary() {
        return mercenary;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }
    public void setDestination(int x, int y){
        destinationX = x;
        destinationY = y;

        patrolling = false;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Troop getTargetEnemy() {
        return targetEnemy;
    }

    public void setTargetEnemy(Troop targetEnemy) {
        this.targetEnemy = targetEnemy;
        patrolling = false;
    }

    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
        patrolling = false;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
        patrolling = false;
    }

    public static Troop getTroopByName(String troopName){
        for(Troop troop : Troops){
            if(troop.getTroopName().equals(troopName)){
                return troop;
            }
        }
        return null;
    }

    public TroopType getTroopType() {
        return troopType;
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    public void update(){
        if(targetEnemy != null){
            if(Map.Distance(x, y, targetEnemy.getX(), targetEnemy.getY()) < attackRange){
                attackEnemy();
                return;
            }
            moveToDestination(targetEnemy.getX(), targetEnemy.getY());
            return;
        }
        if(destinationX != -1 && destinationY != -1){
            if(Map.Distance(x, y, destinationX, destinationY) == 0){
                destinationY = -1;
                destinationX = -1;

                return;
            }
            moveToDestination(destinationX, destinationY);
            return;
        }
        if(patrolling){
            if(patrolIndex == 0){
                if(Map.Distance(x, y, patrolRoute.getX2() , patrolRoute.getY2()) == 0){
                    patrolIndex++;
                    return;
                }
                moveToDestination(patrolRoute.getX2(), patrolRoute.getY2());
            }
            else{
                if(Map.Distance(x, y, patrolRoute.getX1() , patrolRoute.getY1()) == 0){
                    patrolIndex++;
                    return;
                }
                moveToDestination(patrolRoute.getX1(), patrolRoute.getY1());
            }
            return;
        }
        int searchDistance = 0;
        if(state == State.Defensive) searchDistance = 5;
        if(state == State.Standing) searchDistance = 10;
        if(state == State.Offensive) searchDistance = 15;

        //Find The Closest Enemy
        Troop closestEnemy = findClosestEnemy();

        if(Map.Distance(x, y, closestEnemy.getX(), closestEnemy.getY()) < searchDistance){
            targetEnemy = closestEnemy;
        }
        if(GameMenuController.gameMap.tiles[x][y].building != null){
            if(GameMenuController.gameMap.tiles[x][y].building instanceof CastleDefence){
                HP -= ((CastleDefence) GameMenuController.gameMap.tiles[x][y].building).damage;
            }
        }
        if(HP < 0){
            Death();
        }
    }
    public void moveToDestination(int dX, int dY){
        ArrayList<int[]> coordinates;
        coordinates = pathFinding(x , y , dX , dY , GameMenuController.gameMap);
        for (int[] coordinate : coordinates) {
            GameMenuController.gameMap.tiles[coordinate[0]][coordinate[1]].reachable = true;
        }
        GameMenuController.gameMap.tiles[x][y].troops.remove(this);
        if(speed >= coordinates.size()){
            GameMenuController.gameMap.tiles[coordinates.get(coordinates.size() - 1)[0]][coordinates.get(coordinates.size() - 1)[1]].troops.add(this);
            this.x = coordinates.get(coordinates.size() - 1)[0];
            this.y = coordinates.get(coordinates.size() - 1)[1];
        }
        else{
            GameMenuController.gameMap.tiles[coordinates.get(speed)[0]][coordinates.get(speed)[1]].troops.add(this);
            this.x = coordinates.get(speed)[0];
            this.y = coordinates.get(speed)[1];
        }
        clear(GameMenuController.gameMap);
    }
    public ArrayList<int[]> pathFinding(int x1 , int y1 , int x2 , int y2 , Map map){
        ArrayList<int[]> tiles = new ArrayList<>();
        clear(map);
        map.tiles[x1][y1].pathSetter = 0;
        map.tiles[x1][y1].reachable = false;
        for(int i = 0 ; map.tiles[x2][y2].pathSetter == 0 ; i++){
            if(i == 0){
                setAround(x1 , y1 , map);
            }
            else{
                setAround(i , map);
            }
        }
        int[] coordinate = {x2 , y2};
        tiles.add(coordinate);
        for(; true ; ){
            int nextX = findNextX(x2 , y2 , map);
            int nextY = findNextY(x2 , y2 , map);
            x2 = nextX;
            y2 = nextY;
            coordinate = new int[]{nextX, nextY};
            tiles.add(0 , coordinate);
            if(map.tiles[nextX][nextY].pathSetter == 0)
                break;
        }
        return tiles;
    }
    public void setAround(int x, int y, Map map){
        if(x >= 1) {
            if ((map.tiles[x - 1][y].building == null || map.tiles[x - 1][y].building.getOwner().equals(owner)) && map.tiles[x - 1][y].reachable) {
                map.tiles[x - 1][y].pathSetter = map.tiles[x][y].pathSetter + 1;
                map.tiles[x - 1][y].reachable = false;
            } /*else if(!(x == this.x && y == this.y))
                map.tiles[x - 1][y].pathSetter = -1;*/
        }
        if(y >= 1) {
            if ((map.tiles[x][y - 1].building == null || map.tiles[x][y - 1].building.getOwner().equals(owner)) && map.tiles[x][y - 1].reachable) {
                map.tiles[x][y - 1].pathSetter = map.tiles[x][y].pathSetter + 1;
                map.tiles[x][y - 1].reachable = false;
            } /*else if(!(x == this.x && y == this.y))
                map.tiles[x][y - 1].pathSetter = -1;*/
        }
        if(x < map.mapSize - 1) {
            if ((map.tiles[x + 1][y].building == null || map.tiles[x + 1][y].building.getOwner().equals(owner)) && map.tiles[x + 1][y].reachable) {
                map.tiles[x + 1][y].pathSetter = map.tiles[x][y].pathSetter + 1;
                map.tiles[x + 1][y].reachable = false;
            } /*else if(!(x == this.x && y == this.y))
                map.tiles[x + 1][y].pathSetter = -1;*/
        }
        if(y < map.mapSize - 1) {
            if (map.tiles[x][y + 1].building == null && map.tiles[x][y + 1].reachable) {
                map.tiles[x][y + 1].pathSetter = map.tiles[x][y].pathSetter + 1;
                map.tiles[x][y + 1].reachable = false;
            } /*else if(!(x == this.x && y == this.y))
                map.tiles[x][y + 1].pathSetter = -1;*/
        }
    }
    public void setAround(int pathNumber, Map map){
        for(int i = 0 ; i < map.mapSize ; i++){
            for(int j = 0 ; j < map.mapSize ; j++){
                if(map.tiles[i][j].pathSetter == pathNumber)
                    setAround(i , j , map);
            }
        }
    }
    public static void findPath(Map map , ArrayList<Tile> path , int x2 , int y2 , int x1 , int y1){
        path.add(map.tiles[x2][y1]);
        int nextX = findNextX(x2 , y2 , map);
        int nextY = findNextY(x2 , y2 , map);
        while (!(nextX == x1 && nextY == y1)){
            //System.out.println(1);
            path.add(0 , map.tiles[nextX][nextY]);
            int x = nextX;
            int y = nextY;
            nextX = findNextX(x , y , map);
            nextY = findNextY(x , y ,map);
        }
    }
    public static int findNextX(int x , int y , Map map){
        Tile tile = map.tiles[x][y];
        if(x >= 1) {
            if (map.tiles[x - 1][y].pathSetter == tile.pathSetter - 1)
                return (x - 1);
        }
        if(x < map.mapSize - 1) {
            if (map.tiles[x + 1][y].pathSetter == tile.pathSetter - 1)
                return (x + 1);
        }
        return x;
    }
    public static int findNextY(int x , int y , Map map){
        Tile tile = map.tiles[x][y];
        if(x >= 1) {
            if (map.tiles[x - 1][y].pathSetter == tile.pathSetter - 1)
                return y;
        }
        if(x < map.mapSize - 1) {
            if (map.tiles[x + 1][y].pathSetter == tile.pathSetter - 1)
                return y;
        }
        if(y >= 1) {
            if(map.tiles[x][y - 1].pathSetter == tile.pathSetter - 1)
                return (y - 1);
        }
        return (y + 1);
    }
    public static void clear(Map map){
        for(int i = 0 ; i < map.mapSize ; i++){
            for(int j = 0 ; j < map.mapSize ; j++){
                map.tiles[i][j].pathSetter = 0;
            }
        }
    }
    public void Death(){
        ArrayList<Troop> allTroops = GameMenuController.gameMap.allTroops();

        for(Troop troop : allTroops){
            if(troop.targetEnemy == this){
                troop.targetEnemy = null;
            }
        }
        GameMenuController.gameMap.tiles[x][y].troops.remove(this);
        owner.troops.remove(this);
    }
    Troop findClosestEnemy(){

        ArrayList<Troop> allTroops = GameMenuController.gameMap.allTroops();

        int minDistance = (int) Double.POSITIVE_INFINITY;
        Troop CloseTroop = null;

        for(Troop troop : allTroops){
            if(Objects.equals(owner, troop.owner)) continue;
            if(Map.Distance(x, y, troop.getX(), troop.getY()) < minDistance){
                CloseTroop = troop;
            }
        }
        return CloseTroop;
    }
    void attackEnemy(){
        targetEnemy.setHP(targetEnemy.getHP() - attackPower);
    }
    public void SetPatrolRoute(int x1, int y1, int x2, int y2) {

        patrolling = true;

        if(patrolRoute == null){
            patrolRoute = new PatrolRoute(x1, y1, x2, y2);
        }
        else{
            patrolRoute.setX1(x1);
            patrolRoute.setY1(y1);
            patrolRoute.setX2(x2);
            patrolRoute.setY2(y2);
        }
    }
    public int getHP(){
        return HP;
    }
    public void setHP(int HP){
        this.HP = HP;
    }
}
