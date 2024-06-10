public class CardActions {
    private final Enemy enemy;
    private Hero hero;

    public CardActions(Enemy enemy,Hero hero) {

        this.enemy = enemy;
        this.hero = hero;
    }
        public int applyCardActionE (String cardName){
            int damage = 0;

            if (cardName.equals("Images/Карта1.png")) {
                damage = 8;
            }
            if (cardName.equals("Images/Карта2.png")) {
                damage = 11;
            }
            if (cardName.equals("Images/Карта5.png")) {
                damage = 12;
            }
            if (cardName.equals("Images/Карта7.png")) {
                damage = 6;
            }
            if (cardName.equals("Images/Карта8.png")) {
                damage = 6;
            }
            if (cardName.equals("Images/Карта9.png")) {
                damage = 6;
            }
            if (cardName.equals("Images/Карта10.png")) {
                damage = 6;
            }
            if (cardName.equals("Images/Карта14.png")) {
                damage = 5;
            }
            if (cardName.equals("Images/Карта15.png")) {
                damage = 9;
            }

            return damage;
        }
        public int applyCardActionH(String cardName){
            int heal = 0;

            if (cardName.equals("Images/Карта3.png")) {
                heal = 5;
            }
            return heal;

        }

        public int applyCardActionS(String cardName) {
            int shield = 0;

            if (cardName.equals("Images/Карта4.png")) {
                shield = 8;
            }
            if (cardName.equals("Images/Карта6.png")) {
                shield = 11;
            }
            if (cardName.equals("Images/Карта11.png")) {
                shield = 8;
            }
            if (cardName.equals("Images/Карта12.png")) {
                shield = 8;
            }
            if (cardName.equals("Images/Карта13.png")) {
                shield = 8;
            }
            if (cardName.equals("Images/Карта5.png")) {
                shield = 3;
            }
            if (cardName.equals("Images/Карта16.png")) {
                shield = 15;
            }

            return shield;
        }
    }
