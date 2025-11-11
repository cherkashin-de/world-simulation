package supportive;

public enum Constant {;

    public enum EntityType{
        PLACE("place"),
        ANIMAL("animal"),
        GRASS("grass"),
        HUNTER("hunter");

        private final String name;

        EntityType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
