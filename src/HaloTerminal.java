/*==============================================================================
Module: Halo Terminal

Author: t-mthy

Description: Save the Earth from space aliens. Defeat the Boss (Elite) to win!
==============================================================================*/

import java.util.*;

public class HaloTerminal {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        introScreen();

        GameObject.initWorld();

        Player mc = new Player("Master Chief");

        ArrayList<Enemy> alien = new ArrayList<Enemy>();
        alien.add(new Enemy("Grunt"));
        alien.add(new Enemy("Grunt"));
        alien.add(new Enemy("Grunt"));
        alien.add(new Enemy("Grunt"));
        alien.add(new Enemy("Grunt"));
        alien.add(new Enemy("Jackal"));
        alien.add(new Enemy("Jackal"));
        alien.add(new Enemy("Jackal"));
        alien.add(new Enemy("Hunter"));
        alien.add(new Enemy("Hunter"));
        alien.add(new Enemy("Elite"));

        char key = ' ';
        int end = 1;
        while (key != 'q' && key != 'Q' && alien.get(alien.size() - end).health > 0 && mc.health > 0) {
            GameObject.printWorld();
            System.out.println("Enemies on map: " + alien.size() + GameObject.Green);
            System.out.print("Player: " + mc.name + "\tHealth: " + mc.health);
            System.out.println("\tAttack: " + mc.attack + "\tShield: " + mc.shield + GameObject.Red);
            System.out.print("Boss: " + alien.get(alien.size() - end).type);
            System.out.print("\t\tHealth: " + alien.get(alien.size() - end).health);
            System.out.print("\tAttack: " + alien.get(alien.size() - end).attack);
            System.out.println("\tShield: " + alien.get(alien.size() - end).shield + GameObject.Default);
            System.out.print("\nEnter W,A,S,D to move (or \"Q\" to quit): ");
            key = input.next().charAt(0);

            // move player
            if (key == 'w' || key == 'W')
                mc.moveUp();
            else if (key == 'a' || key == 'A')
                mc.moveLeft();
            else if (key == 's' || key == 'S')
                mc.moveDown();
            else if (key == 'd' || key == 'D')
                mc.moveRight();

            // player face-to-face w/ enemies
            for (int i = 0; i < alien.size(); i++) {
                if ((alien.get(i).row == mc.row && (alien.get(i).col == mc.col + 1))
                        || (alien.get(i).row == mc.row && (alien.get(i).col == mc.col - 1))
                        || (alien.get(i).col == mc.col && (alien.get(i).row == mc.row + 1))
                        || (alien.get(i).col == mc.col && (alien.get(i).row == mc.row - 1))) {

                    // player attacks enemy
                    if (alien.get(i).shield > 0)
                        alien.get(i).shield -= mc.attack;
                    else if (alien.get(i).shield <= 0)
                        alien.get(i).health -= mc.attack;

                    // enemy toasts (don't remove last index, boss)
                    if (alien.get(i).health <= 0 && i != alien.size() - end) {
                        if ((int) (Math.random() * 7) == 0)
                            GameObject.world[alien.get(i).row][alien.get(i).col] = '$';
                        else if ((int) (Math.random() * 5) == 0)
                            GameObject.world[alien.get(i).row][alien.get(i).col] = '+';
                        else
                            GameObject.world[alien.get(i).row][alien.get(i).col] = ' ';
                        alien.remove(i);
                    } else {
                        // enemy attacks player (ONLY if enemy is still alive)
                        if (mc.shield > 0)
                            mc.shield -= alien.get(i).attack;
                        else if (mc.shield <= 0)
                            mc.health -= alien.get(i).attack;
                    }
                }
            }

            // move enemies
            for (int i = 0; i < alien.size(); i++) {
                if ((Math.abs(mc.col - alien.get(i).col) <= alien.get(i).range) &&
                        (Math.abs(mc.row - alien.get(i).row) <= alien.get(i).range)) {

                    if (alien.get(i).col > mc.col)
                        alien.get(i).moveLeft();
                    else
                        alien.get(i).moveRight();
                    if (alien.get(i).row > mc.row)
                        alien.get(i).moveUp();
                    else
                        alien.get(i).moveDown();
                }
            }
        } // end game loop

        // game result: win or lose
        if (key != 'q' && key != 'Q' && mc.health <= 0)
            loseScreen();
        else if (key != 'q' && key != 'Q' && alien.get(alien.size() - end).health <= 0)
            winScreen();

        input.close();
    } // end main

    static void introScreen() {
        System.out.print(GameObject.CLS + GameObject.Cyan);
        System.out.println("\t  _   _    _    _     ___    _____ _____ ____  __  __ ___ _   _    _    _     ");
        System.out.println("\t | | | |  / \\  | |   / _ \\  |_   _| ____|  _ \\|  \\/  |_ _| \\ | |  / \\  | |    ");
        System.out.println("\t | |_| | / _ \\ | |  | | | |   | | |  _| | |_) | |\\/| || ||  \\| | / _ \\ | |    ");
        System.out.println("\t |  _  |/ ___ \\| |__| |_| |   | | | |___|  _ <| |  | || || |\\  |/ ___ \\| |___ ");
        System.out.println("\t |_| |_/_/   \\_\\_____\\___/    |_| |_____|_| \\_\\_|  |_|___|_| \\_/_/   \\_\\_____|");
        System.out.println("\n\n          . .          ");
        System.out.print("          . .          \t\t\t" + GameObject.Default);
        System.out.println("Save the Earth from space aliens." + GameObject.Cyan);
        System.out.print("          : :          \t\t\t" + GameObject.Default);
        System.out.println("Defeat the Boss (Elite) to win!" + GameObject.Cyan);
        System.out.println("         .^ ^.         ");
        System.out.println("         :^ ^:         ");
        System.out.print("         ^^ ^^         \t\t\t" + GameObject.Blue);
        System.out.println("Human\t\t\tHealth\tAttack\tShield" + GameObject.Cyan);
        System.out.print("         !^ ^~         \t\t\t" + GameObject.Green);
        System.out.print("M" + GameObject.Default + " - ");
        System.out.println("Master Chief\t300\t100\t300" + GameObject.Cyan);
        System.out.println("        .7^ ^7.        ");
        System.out.println("        :?^ ^?:        ");
        System.out.print("        ~?^ ^?^        \t\t\t" + GameObject.Blue);
        System.out.println("Aliens\t\t\tHealth\tAttack\tShield" + GameObject.Cyan);
        System.out.print("       .!J^ ^J!.       \t\t\t" + GameObject.Yellow);
        System.out.print("G" + GameObject.Default + " - ");
        System.out.println("Grunt\t\t100\t20\t100" + GameObject.Cyan);
        System.out.print("       :7J^ ^J7:       \t\t\t" + GameObject.Magenta);
        System.out.print("J" + GameObject.Default + " - ");
        System.out.println("Jackal\t\t100\t40\t200" + GameObject.Cyan);
        System.out.print("       ^?J^ ^Y?^       \t\t\t" + GameObject.Cyan);
        System.out.print("H" + GameObject.Default + " - ");
        System.out.println("Hunter\t\t50\t60\t700" + GameObject.Cyan);
        System.out.print("      .~JJ^ ^YJ~.      \t\t\t" + GameObject.Red);
        System.out.print("E" + GameObject.Default + " - ");
        System.out.println("Elite\t\t500\t200\t300" + GameObject.Cyan);
        System.out.println("      :7YJ^ ^YY7:      ");
        System.out.println("     .~JJ7^ ^?YJ~.     ");
        System.out.print("     ^?Y?~   ~?Y?^     \t\t\t" + GameObject.Blue);
        System.out.println("Power-Ups\t\tEffects" + GameObject.Cyan);
        System.out.print("    :7YYJ!   !JYY7:    \t\t\t" + GameObject.White);
        System.out.print("+" + GameObject.Default + " - ");
        System.out.println("Health-Up\t\t+50 Health" + GameObject.Cyan);
        System.out.print("   :7YYJ7^   ^7Y5Y7.   \t\t\t" + GameObject.White);
        System.out.print("$" + GameObject.Default + " - ");
        System.out.println("Attack-Up\t\t+25 Attack" + GameObject.Cyan);
        System.out.println("  :7YY!.   ^   .!Y57:  ");
        System.out.println(" .!55JJ7~~:::~~7JJ55!. ");
        System.out.println("  :?YJ57J?!!!?J!5J5?:  ");
        System.out.print("   .!~..  .:.  ..~!.   \t\t\t" + GameObject.Default);
        System.out.println("Press <Enter> To Begin..." + GameObject.Cyan);
        System.out.println("     .           .     " + GameObject.Default);
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }

    static void loseScreen() {
        System.out.print(GameObject.CLS + GameObject.Red);
        System.out.println("\t\t          _______                 _        _______  _______  _______          ");
        System.out.println("\t\t|\\     /|(  ___  )|\\     /|      ( \\      (  ___  )(  ____ \\(  ____ \\         ");
        System.out.println("\t\t( \\   / )| (   ) || )   ( |      | (      | (   ) || (    \\/| (    \\/         ");
        System.out.println("\t\t \\ (_) / | |   | || |   | |      | |      | |   | || (_____ | (__             ");
        System.out.println("\t\t  \\   /  | |   | || |   | |      | |      | |   | |(_____  )|  __)            ");
        System.out.println("\t\t   ) (   | |   | || |   | |      | |      | |   | |      ) || (               ");
        System.out.println("\t\t   | |   | (___) || (___) |      | (____/\\| (___) |/\\____) || (____/\\ _  _  _ ");
        System.out.println("\t\t   \\_/   (_______)(_______)      (_______/(_______)\\_______)(_______/(_)(_)(_)\n\n");
        System.out.println("\t\t\t                    !PB&&@&BP!                    ");
        System.out.println("\t\t\t!P?^.            .!5@@@@@@@@@@5~.            .~?P~");
        System.out.println("\t\t\t.G@@#BJ       .!5PJB@@@@@@@@@@@5PY~        YB&@@P ");
        System.out.println("\t\t\t .P@@@@!    ~YG5! ~B@@@@@@@@@B@P:!PGY^    ?@@@@5  ");
        System.out.println("\t\t\t   ?#@@@Y^?PP?.  ^B5@@@@@@@@@P&@Y  :?55?~5@@@B!   ");
        System.out.println("\t\t\t    .75@@@@?     J&G@@@@@@&G7?G&@^    ?@@@@5!.    ");
        System.out.println("\t\t\t       ~B@@@#J: :5@&@@@@@@@&#5#@@P.^J#@@@G^       ");
        System.out.println("\t\t\t       .#G?#@@&PGP@@@@@@@@@@@&@@@@#@@@B?GB        ");
        System.out.println("\t\t\t       ~@5 .7G@@5J@@@@@@@@@@@@@@@@@@P!  5@^       ");
        System.out.println("\t\t\t       J@J    ~#B?@@@@@@@@@@@@@@@@#^    5@?       ");
        System.out.println("\t\t\t       G@J     :P&@@@@@@@@@@@@@@@&~     Y@P       ");
        System.out.println("\t\t\t      ~@@!      7&@@@@@@@@@@@@@@@Y      7@&^      ");
        System.out.println("\t\t\t    ~P#5^      .5P@@@@@@@@@@@@@@PY       ~5#5~    ");
        System.out.println("\t\t\t   ^@P:       ~Y!P5JG@@#&&#@@GYPP?Y^       :G@:   ");
        System.out.println("\t\t\t   7@Y     :?B@J^!BY?5##&&##5?YB~!5@G7.     5@~   ");
        System.out.println("\t\t\t   .7#B!.!P&@@P?G?:.~PB@@@@B5~.^?G?B@@#Y~.7#B!    ");
        System.out.println("\t\t\t     :P#&@@@G?:^B@#?. .^^^^. .?&@G::JB@@@&B5.     ");
        System.out.println("\t\t\t   ~5#@@@#Y^    ^J?Y7.~.  :~.7Y7J^    ^5&@@@BJ^   ");
        System.out.println("\t\t\t~7G@@@@B7. YG!     #@#@7  ?@&@#     7GJ .?B@@@&5!^");
        System.out.println("\t\t\tY@&&@P!    ^P@G!.  Y#&P^  ^G&#Y  .!G@5^    !P@#@@?");
        System.out.println("\t\t\t B@B^        ~P@#~  .:      :.  !&@P^        ~#@P ");
        System.out.println("\t\t\t !@Y           !#@P^  ^J!~J:  ^P@B~          .G&^ ");
        System.out.println("\t\t\t  :              ?#@PP@@!!@@PP@#7              :  ");
        System.out.println("\t\t\t                  .J&@&~  !&@&?.                  ");
        System.out.println("\n\t\t\tElite says: \"Wort, Wort, Wort! Aaaawubadugh!\"\n\n" + GameObject.Default);
    }

    static void winScreen() {
        System.out.print(GameObject.CLS + GameObject.Green);
        System.out.println("\t\t____    ____  ______    __    __     ____    __    ____  __  .__   __.  __ ");
        System.out.println("\t\t\\   \\  /   / /  __  \\  |  |  |  |    \\   \\  /  \\  /   / |  | |  \\ |  | |  |");
        System.out.println("\t\t \\   \\/   / |  |  |  | |  |  |  |     \\   \\/    \\/   /  |  | |   \\|  | |  |");
        System.out.println("\t\t  \\_    _/  |  |  |  | |  |  |  |      \\            /   |  | |  . `  | |  |");
        System.out.println("\t\t    |  |    |  `--'  | |  `--'  |       \\    /\\    /    |  | |  |\\   | |__|");
        System.out.println("\t\t    |__|     \\______/   \\______/         \\__/  \\__/     |__| |__| \\__| (__)\n\n");
        System.out.println("\t\t\t         .~7YPB#&&&&&&&&&&&&&&&&&&BG5J!^          ");
        System.out.println("\t\t\t        !B@@@@@@@@@###########&@@@@@@@@@P:        ");
        System.out.println("\t\t\t       J@@@@@@@@@@@~          Y@@@@@@@@@@#~       ");
        System.out.println("\t\t\t      5@@@@@@@@@@@@5!!!!!!!!!7B@@@@@@@@@@@@!      ");
        System.out.println("\t\t\t     J@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@~     ");
        System.out.println("\t\t\t    ~@@@@@@@@@@@@@@#YYYYYYYYY5&@@@@@@@@@@@@@B.    ");
        System.out.println("\t\t\t   ^B@@@@@@@@@@@@@@&#########&@@@@@@@@@@@@@@@5.   ");
        System.out.println("\t\t\t .Y@@@@@@&&#GP5Y5GGGPPPPGGPPPGGGGYY5GB#&@@@@@@#7  ");
        System.out.println("\t\t\t ^@@@@B&5:.  .~YPJ77777777777777YPJ^  ..~B##@@@G  ");
        System.out.println("\t\t\t  P@@#:!B5JJYPY!75GGGGGGGGGGGGGGY!?P5YJJPP.7@@@7  ");
        System.out.println("\t\t\t  5@@&^ :^^!!~?B@@@@@@@@@@@@@@@@@&P7~!~^^  J@@@~  ");
        System.out.println("\t\t\t  ?@@@7   ?@&&@@@@@@@@@@@@@@@@@@@@@@&&#:   G@@&:  ");
        System.out.println("\t\t\t  J@@@#:  ^G@@@@@@@@@@@@@@@@@@@@@@@@@@Y.  7@@@@~  ");
        System.out.println("\t\t\t 7@@@@@B.   7G####P7~~~^^^~~~~JB&###5^   ~@@@@@#: ");
        System.out.println("\t\t\t!&GYP@@@P~.   ....  ^JYYYYYY7.  ...    :7#@@#Y5&B.");
        System.out.println("\t\t\t#Y  :&@@@@&GPYJ?7!!5&PJJJJJJB#J!7?JJ5PB&@@@@P  :BY");
        System.out.println("\t\t\t&J  .B@@@@@@@@@@@@@@@?     :P@@@@@@@@@@@@@@@Y   B5");
        System.out.println("\t\t\t7BGJ^G@@@@@@@@@@@@@&&@#B###&@#@@@@@@@@@@@@@@?~YBP:");
        System.out.println("\t\t\t .!5B&@G!Y#@@@@@@@@G~&@@@@@@G~&@@@@@@@@G?7&@BGJ^  ");
        System.out.println("\t\t\t    .~G@#5?Y#@@@@@@#^#@@@@@@Y!@@@@@@@G??P&&Y:     ");
        System.out.println("\t\t\t       ~YB#GB@@@@@@&^G@@@@@@??@@@@@@#PB#P?^       ");
        System.out.println("\t\t\t          .:!Y#@@@@@~5@@@@@@!Y@@@@@G?^..          ");
        System.out.println("\t\t\t              .!YB&@7J@&##@@~G@#P?^               ");
        System.out.println("\t\t\t                  ^?~~Y~.:?Y:?7:                  ");
        System.out.println("\n\t\t\t\t\tNice Work Chief!\n\n" + GameObject.Default);
    }
}

class GameObject {
    static String CLS = "\033[2J\033[1;1H";
    static String Red = "\033[31;1m";
    static String Green = "\033[32;1m";
    static String Yellow = "\033[33;1m";
    static String Blue = "\033[34;1m";
    static String Magenta = "\033[35;1m";
    static String Cyan = "\033[36;1m";
    static String White = "\033[37;1m";
    static String Default = "\033[0m";
    static String WhiteOnBlue = "\033[44;1m";
    static final int ROWS = 20, COLS = 80;
    static char world[][] = new char[ROWS][COLS];
    int row, col;
    int randRow, randCol;
    char avatar;
    int health, attack, shield;
    int healthUps = 5, attackUps = 5;
    int healthAdd = 50, attackAdd = 25;
    int keepMoving;

    static void initWorld() {
        GameObject obj = new GameObject(); // access using object

        // build map & boundaries
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (r == 0 || c == 0 || c == (COLS - 1) || r == (ROWS - 1))
                    world[r][c] = '#';
                else
                    world[r][c] = ' ';
            }
        }

        // boss chamber
        for (int c = (COLS - 10); c < (COLS - 1); c++) {
            world[8][c] = '#';
            world[11][c] = '#';
        }

        // obstacle: trees 1
        obj.row = 5;
        obj.col = 10;
        for (int c = obj.col; c < (obj.col + 11); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row][c] = ' ';
            else
                world[obj.row][c] = '|';
        }
        for (int c = obj.col; c < (obj.col + 12); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 1][c] = '(';
            else
                world[obj.row - 1][c] = ')';
        }
        for (int c = obj.col + 1; c < (obj.col + 11); c++) {
            if (c == obj.col + 1 || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 2][c] = '(';
            else
                world[obj.row - 2][c] = ')';
        }

        // obstacle: trees 2
        obj.row = 16;
        obj.col = 25;
        for (int c = obj.col; c < (obj.col + 11); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row][c] = ' ';
            else
                world[obj.row][c] = '|';
        }
        for (int c = obj.col; c < (obj.col + 12); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 1][c] = '(';
            else
                world[obj.row - 1][c] = ')';
        }
        for (int c = obj.col + 1; c < (obj.col + 11); c++) {
            if (c == obj.col + 1 || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 2][c] = '(';
            else
                world[obj.row - 2][c] = ')';
        }

        // obstacle: trees 3
        obj.row = 9;
        obj.col = 45;
        for (int c = obj.col; c < (obj.col + 11); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row][c] = ' ';
            else
                world[obj.row][c] = '|';
        }
        for (int c = obj.col; c < (obj.col + 12); c++) {
            if (c == obj.col || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 1][c] = '(';
            else
                world[obj.row - 1][c] = ')';
        }
        for (int c = obj.col + 1; c < (obj.col + 11); c++) {
            if (c == obj.col + 1 || c == (obj.col + 3) || c == (obj.col + 7))
                world[obj.row - 2][c] = '(';
            else
                world[obj.row - 2][c] = ')';
        }

        // powerups static spawn
        world[ROWS - ((ROWS / 4) + (ROWS / 2)) - 1][COLS - 5] = '$';
        world[ROWS - ((ROWS / 4) + (ROWS / 2)) + 1][COLS - 5] = '$';
        world[ROWS - ((ROWS / 4) + (ROWS / 2)) + 1][COLS - 3] = '$';
        world[ROWS - ((ROWS / 4) + (ROWS / 2)) - 1][COLS - 3] = '+';
        world[ROWS - ((ROWS / 4) + (ROWS / 2))][COLS - 4] = '+';

        world[ROWS - (ROWS / 4) - 1][COLS - 5] = '$';
        world[ROWS - (ROWS / 4) + 1][COLS - 5] = '$';
        world[ROWS - (ROWS / 4) - 1][COLS - 3] = '$';
        world[ROWS - (ROWS / 4) + 1][COLS - 3] = '+';
        world[ROWS - (ROWS / 4)][COLS - 4] = '+';

        // powerups RNG spawn
        for (int i = 0; i < obj.healthUps; i++) {
            obj.rng2D();
            world[obj.row][obj.col] = '+';
        }
        for (int i = 0; i < obj.attackUps; i++) {
            obj.rng2D();
            world[obj.row][obj.col] = '$';
        }
    }

    static void printWorld() {
        System.out.print(CLS);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                if (world[r][c] == ' ')
                    System.out.print(' ');
                else if (world[r][c] == '#')
                    System.out.print(WhiteOnBlue + ' ' + Default);
                else if (world[r][c] == '$')
                    System.out.print(White + '$' + Default);
                else if (world[r][c] == '+')
                    System.out.print(White + '+' + Default);
                else if (world[r][c] == 'G')
                    System.out.print(Yellow + 'G' + Default);
                else if (world[r][c] == 'J')
                    System.out.print(Magenta + 'J' + Default);
                else if (world[r][c] == 'H')
                    System.out.print(Cyan + 'H' + Default);
                else if (world[r][c] == 'E')
                    System.out.print(Red + 'E' + Default);
                else if (world[r][c] == 'M')
                    System.out.print(Green + 'M' + Default);
                else
                    System.out.print(world[r][c]);
            }
            System.out.println();
        }
    }

    // Random Number Generator
    void rng2D() {
        randRow = 0;
        randCol = 0;

        while (world[randRow][randCol] != ' ') {
            randRow = (int) (Math.random() * ROWS);
            randCol = (int) (Math.random() * COLS);

            // prevent boss spawn being taken
            if (randRow == ROWS / 2 && randCol == COLS - 2)
                continue;
        }

        row = randRow;
        col = randCol;
    }

    // 1 out of # chance of enemy not moving
    void rngMove() {
        keepMoving = (int) (Math.random() * 5);
    }
}

class Enemy extends GameObject {
    String type;
    int range;

    Enemy(String aType) {
        type = aType;

        if (type.equals("Grunt")) {
            avatar = 'G';
            health = 100;
            attack = 20;
            shield = 100;
            range = 4;
            rng2D();
            world[row][col] = avatar;
        }

        if (type.equals("Jackal")) {
            avatar = 'J';
            health = 100;
            attack = 40;
            shield = 200;
            range = 6;
            rng2D();
            world[row][col] = avatar;
        }

        if (type.equals("Hunter")) {
            avatar = 'H';
            health = 50;
            attack = 60;
            shield = 700;
            range = 2;
            row = ROWS - ((ROWS / 4) + (ROWS / 2));
            col = COLS - (COLS / 8);
            if (world[row][col] == ' ')
                world[row][col] = avatar;
            else {
                row = ROWS - (ROWS / 4);
                col = COLS - (COLS / 8);
                world[row][col] = avatar;
            }
        }

        if (type.equals("Elite")) {
            avatar = 'E';
            health = 500;
            attack = 200;
            shield = 300;
            range = 1;
            row = ROWS / 2;
            col = COLS - 2;
            world[row][col] = avatar;
        }
    }

    void moveRight() {
        rngMove();
        if (keepMoving != 0) {
            if (world[row][col + 1] == ' ') {
                world[row][col] = ' ';
                col++;
                world[row][col] = avatar;
            }
        }
    }

    void moveLeft() {
        rngMove();
        if (keepMoving != 0) {
            if (world[row][col - 1] == ' ') {
                world[row][col] = ' ';
                col--;
                world[row][col] = avatar;
            }
        }
    }

    void moveUp() {
        rngMove();
        if (keepMoving != 0) {
            if (world[row - 1][col] == ' ') {
                world[row][col] = ' ';
                row--;
                world[row][col] = avatar;
            }
        }
    }

    void moveDown() {
        rngMove();
        if (keepMoving != 0) {
            if (world[row + 1][col] == ' ') {
                world[row][col] = ' ';
                row++;
                world[row][col] = avatar;
            }
        }
    }
}

class Player extends GameObject {
    String name;

    Player(String aName) {
        name = aName;
        avatar = 'M';
        health = 300;
        attack = 100;
        shield = 300;
        row = 1;
        col = 1;
        world[row][col] = avatar;
    }

    void moveRight() {
        if (world[row][col + 1] == ' ' || world[row][col + 1] == '+' || world[row][col + 1] == '$') {
            if (world[row][col + 1] == '+')
                health += healthAdd;
            if (world[row][col + 1] == '$')
                attack += attackAdd;
            world[row][col] = ' ';
            col++;
            world[row][col] = avatar;
        }
    }

    void moveLeft() {
        if (world[row][col - 1] == ' ' || world[row][col - 1] == '+' || world[row][col - 1] == '$') {
            if (world[row][col - 1] == '+')
                health += healthAdd;
            if (world[row][col - 1] == '$')
                attack += attackAdd;
            world[row][col] = ' ';
            col--;
            world[row][col] = avatar;
        }
    }

    void moveUp() {
        if (world[row - 1][col] == ' ' || world[row - 1][col] == '+' || world[row - 1][col] == '$') {
            if (world[row - 1][col] == '+')
                health += healthAdd;
            if (world[row - 1][col] == '$')
                attack += attackAdd;
            world[row][col] = ' ';
            row--;
            world[row][col] = avatar;
        }
    }

    void moveDown() {
        if (world[row + 1][col] == ' ' || world[row + 1][col] == '+' || world[row + 1][col] == '$') {
            if (world[row + 1][col] == '+')
                health += healthAdd;
            if (world[row + 1][col] == '$')
                attack += attackAdd;
            world[row][col] = ' ';
            row++;
            world[row][col] = avatar;
        }
    }
}
