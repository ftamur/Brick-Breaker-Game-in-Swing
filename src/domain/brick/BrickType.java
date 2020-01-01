package domain.brick;

public enum BrickType {
   
	WrapperBrick,
	SimpleBrick,
	MineBrick,
    HalfMetalBrick;


    public static BrickType fromInt(int x) {
        switch (x) {
            case 0:
                return WrapperBrick;
            case 1:
                return SimpleBrick;
            case 2:
                return MineBrick;
            case 3:
                return HalfMetalBrick;
            default:
                return null;
        }
    }


    public static int toInt(BrickType type) {
        switch (type) {
            case WrapperBrick:
                return 0;
            case SimpleBrick:
                return 1;
            case MineBrick:
                return 2;
            case HalfMetalBrick:
                return 3;
            default:
                return -1;
        }
    }

}
