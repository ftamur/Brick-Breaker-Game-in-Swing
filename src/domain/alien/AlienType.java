package domain.alien;

public enum AlienType {
	
	REPAIRING,
	COOPERATIVE,
	PROTECTING,
	DRUNK;

	public static AlienType fromInt(int x) {
		switch (x) {
			case 0:
				return REPAIRING;
			case 1:
				return COOPERATIVE;
			case 2:
				return PROTECTING;
			case 3:
				return DRUNK;
			default:
				return null;
		}
	}


	public static int toInt(AlienType type) {
		switch (type) {
			case REPAIRING:
				return 0;
			case COOPERATIVE:
				return 1;
			case PROTECTING:
				return 2;
			case DRUNK:
				return 3;
			default:
				return -1;
		}
	}
	
}
