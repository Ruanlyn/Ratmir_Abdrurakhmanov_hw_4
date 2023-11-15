import java.util.Random;

public class Main {
    public static int bossHealth = 400;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 200, 300, 200, 250};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 10, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Thor"};
    public static int roundNumber;

    public static int helpMedic = regenerableHealth();

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        deadOrAlive();
        if (bossHealth > 0) {
            chooseBossDefence();
            bossAttack();
        }
        roundNumber++;
        //chooseBossDefence();
        //bossAttack();
        heroesAttack();
        printStatistics();
        //addHealth();
        Golem();
        Lucky();
        Thor();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttack() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " -------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }

    public static int regenerableHealth() {
        int beginHealth = 10;
        int endHealth = 30;
        int totalHealth = beginHealth + (int) (Math.random() * endHealth);
        return totalHealth;
    }

    public static void addHealth() {
        Random random = new Random();
        int choseHeroes = random.nextInt(3);
        for (int i = 0; i < heroesHealth[choseHeroes]; i++) {
            if (heroesHealth[choseHeroes] < 100 && heroesHealth[choseHeroes] > 0) ;
            {
                heroesHealth[choseHeroes] = heroesHealth[choseHeroes] + helpMedic;
                System.out.println("Медик вылечил: " + heroesAttackType[choseHeroes] + " On " + helpMedic);
                break;
            }
        }
    }

    public static boolean deadOrAlive() {
        if (heroesHealth[3] <= 0) {
            System.out.println("Медик умер");
            return true;
        }
        addHealth();
        return false;
    }

    public static void Golem() {
        Random random = new Random();
        //int choseHeroes = random.nextInt(3) + 1;
        for (int i = 0; i < heroesHealth.length; i++) {
            heroesHealth[i] = heroesHealth[i] + 13;
        }
        heroesHealth[4] = heroesHealth[4] - 60;
        System.out.println("Голем преминил способность");
    }

    public static void Lucky() {
        Random random = new Random();
        boolean dodgeDamage = random.nextBoolean();
        if (heroesHealth[5] > 0) {
            if (!(dodgeDamage)) {
                heroesHealth[5] = heroesHealth[5] + bossDamage;
                if (heroesHealth[5] > 130) {
                    heroesHealth[5] = heroesHealth[5] - bossDamage;
                }
                System.out.println("Lucky увернулся");
            }
        }
    }

    public static void Thor() {
        Random random = new Random();
        boolean deafenDamage = random.nextBoolean();
        if (heroesHealth[6] > 0 && deafenDamage) {
            bossDamage = 0;
            System.out.println("Босс оглушён");
        } else if (heroesHealth[6] > 0 && !(deafenDamage)) {
            System.out.println("Промах способности босс не оглушен");
        }
    }
}