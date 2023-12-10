#include <iostream>
#include <vector>
#include <string>
#include <conio.h>
#include <thread>
#include <chrono>



const int SCREEN_WIDTH = 40;
const int SCREEN_HEIGHT = 20;
const int SHOOTING_ALIEN_ROWS = 1;
const int ALIEN_ROWS = 2;
const int TANK_ALIEN_ROWS = 2;
const int ALIEN_COLUMNS = 10;
const int GAME_SPEED = 70;



class Player {
public:
    int x, y;

    Player(int startX, int startY) : x(startX), y(startY) {}

    void moveLeft() {
        if (x > 0) {
            x--;
        }
    }

    void moveRight() {
        if (x < SCREEN_WIDTH - 1) {
            x++;
        }
    }
};


class Alien {
public:
    int x, y;
    bool alive;

    Alien(int startX, int startY) : x(startX), y(startY), alive(true) {}
};

class AlienRow {
public:
    std::vector<Alien> aliens;
    bool movingRight;

    AlienRow(int startX, int startY, int count, int spacing) : movingRight(true) {
        for (int i = 0; i < count; ++i) {
            Alien newAlien(2 + startX + i * spacing, startY + 2);
            aliens.push_back(newAlien);
        }
    }

    void move() {
        if (aliens.empty()) {
            return; 
        }

        if ((movingRight && aliens.back().x >= SCREEN_WIDTH - 1) ||
            (!movingRight && aliens.front().x <= 0)) {
            movingRight = !movingRight; 
            for (auto& alien : aliens) {
                alien.y++; 
            }
        }
        else {
            int step;
            if (movingRight == true) {
                step = 1;
            }
            else {
                step = -1;
            }
            for (auto& alien : aliens) {
                alien.x += step;
            }
        }
    }
    void removeDeadAliens() {
        aliens.erase(std::remove_if(aliens.begin(), aliens.end(), [](const Alien& a) { return !a.alive; }), aliens.end());
    }

};

class Bunker {
public:
    int x, y;
    int health;

    Bunker(int startX, int startY, int startHealth) : x(startX), y(startY), health(startHealth) {}

    void takeDamage() {
        if (health > 0) {
            health--;
        }
    }

    bool isDestroyed() const {
        return health <= 0;
    }
};

class Bullet {
public:
    int x, y;
    bool active;

    Bullet(int startX, int startY) : x(startX), y(startY), active(true) {}

    void move() {
        if (y > 0) {
            y --;
        }
        else {
            active = false;
        }
    }
};



class GameState {
public:
    Player player;
    std::vector<AlienRow> shooting_alienRows;
    std::vector<AlienRow> alienRows;
    std::vector<AlienRow> tank_alienRows;
    Bullet bullet;
    std::vector<Bunker> bunkers;
    int score;
    bool bulletActive = false;  

    GameState() : player(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 1), bullet(0, 0), bulletActive(false) {

        for (int row = 0; row < SHOOTING_ALIEN_ROWS; ++row) {
            AlienRow newRow(0, row * 2, ALIEN_COLUMNS, 3);
            shooting_alienRows.push_back(newRow);
        }

        for (int row = 0; row < ALIEN_ROWS; ++row) {
            AlienRow newRow(0, (SHOOTING_ALIEN_ROWS + row) * 2, ALIEN_COLUMNS, 3);
            alienRows.push_back(newRow);
        }

        for (int row = 0; row < TANK_ALIEN_ROWS; ++row) {
            AlienRow newRow(0, (SHOOTING_ALIEN_ROWS + ALIEN_ROWS + row) * 2, ALIEN_COLUMNS, 3);
            tank_alienRows.push_back(newRow);
        }

        int bunkerCount = 7;
        int bunkerSpacing = SCREEN_WIDTH / (bunkerCount + 1);
        int bunkerY = SCREEN_HEIGHT - 3;
        for (int i = 0; i < bunkerCount; ++i) {
            Bunker newBunker(bunkerSpacing * (i + 1), bunkerY, 5); // 5 - bunkerio gyvybes
            bunkers.push_back(newBunker);
        }
    }
    void checkBulletBunkerCollision() {
        if (!bulletActive) return;

        for (auto& bunker : bunkers) {
            if (bullet.x == bunker.x && bullet.y == bunker.y && !bunker.isDestroyed()) {
                bunker.takeDamage();
                bullet.active = false;
                bulletActive = false;
                break; 
            }
        }
    }
    bool areAllAliensDead() const {
        for (const auto& row : shooting_alienRows) {
            for (const auto& alien : row.aliens) {
                if (alien.alive) {
                    return false;
                }
            }
        }

        for (const auto& row : alienRows) {
            for (const auto& alien : row.aliens) {
                if (alien.alive) {
                    return false;
                }
            }
        }

        for (const auto& row : tank_alienRows) {
            for (const auto& alien : row.aliens) {
                if (alien.alive) {
                    return false;
                }
            }
        }

        return true; 
    }
    void checkAlienBunkerCollisions() {
        for (auto& bunker : bunkers) {
            if (bunker.isDestroyed()) {
                continue;
            }

            for (auto& row : shooting_alienRows) {
                for (auto& alien : row.aliens) {
                    if (alien.x == bunker.x && alien.y == bunker.y && alien.alive) {
                        for (int i = 0; i < 5; i++)
                        bunker.takeDamage();
                        if (bunker.isDestroyed()) {
                            alien.alive = false;
                        }
                    }
                }
            }

            for (auto& row : alienRows) {
                for (auto& alien : row.aliens) {
                    if (alien.x== bunker.x && alien.y == bunker.y && alien.alive) {
                        for( int i = 0; i < 5; i++)
                            bunker.takeDamage();
                        if (bunker.isDestroyed()) {
                            alien.alive = false;
                        }
                    }
                }
            }

            for (auto& row : tank_alienRows) {
                for (auto& alien : row.aliens) {
                    if (alien.x == bunker.x && alien.y == bunker.y && alien.alive) {
                        for (int i = 0; i < 5; i++)
                        bunker.takeDamage();
                        if (bunker.isDestroyed()) {
                            alien.alive = false;
                        }
                    }
                }
            }
        }
    }



};



bool checkPlayerAlienCollision(const Player& player, const std::vector<AlienRow>& shootingAlienRows, const std::vector<AlienRow>& alienRows, const std::vector<AlienRow>& tankAlienRows) {

    for (const auto& row : shootingAlienRows) {
        for (const auto& alien : row.aliens) {
            if (alien.alive && alien.x == player.x && alien.y == player.y) {
                return true; 
            }
        }
    }


    for (const auto& row : alienRows) {
        for (const auto& alien : row.aliens) {
            if (alien.alive && alien.x == player.x && alien.y == player.y) {
                return true; 
            }
        }
    }


    for (const auto& row : tankAlienRows) {
        for (const auto& alien : row.aliens) {
            if (alien.alive && alien.x == player.x && alien.y == player.y) {
                return true; 
            }
        }
    }

    return false; 
}



void drawGameField(const GameState& gameState) {

    system("cls");


    std::cout << std::string(SCREEN_WIDTH + 2, '-') << '\n';


    for (int y = 0; y < SCREEN_HEIGHT; ++y) {
        std::cout << '|';
        for (int x = 0; x < SCREEN_WIDTH; ++x) {

            bool drawn = false;
            if (gameState.player.x == x && gameState.player.y == y) {
                std::cout << 'A';
                drawn = true;
            }

            for (const auto& row : gameState.shooting_alienRows) {
                for (const auto& alien : row.aliens) {
                    if (alien.x == x && alien.y == y && alien.alive) {
                        std::cout << 'Y';
                        drawn = true;
                        break;
                    }
                }
                if (drawn) break;
            }

            for (const auto& row : gameState.alienRows) {
                for (const auto& alien : row.aliens) {
                    if (alien.x == x && alien.y == y && alien.alive) {
                        std::cout << 'o';
                        drawn = true;
                        break;
                    }
                }
                if (drawn) break;
            }
            for (const auto& row : gameState.tank_alienRows) {
                for (const auto& alien : row.aliens) {
                    if (alien.x == x && alien.y == y && alien.alive) {
                        std::cout << 'M';
                        drawn = true;
                        break;
                    }
                }

                if (drawn) break;
            }

            for (const auto& bunker : gameState.bunkers) {
                if (bunker.x == x && bunker.y == y && !bunker.isDestroyed()) {
                    std::cout << '#';
                    drawn = true;
                    break;
                }
            }

            if (!drawn && gameState.bulletActive && gameState.bullet.x == x && gameState.bullet.y == y) {
                std::cout << '|';
                drawn = true;
            }

                if (!drawn) {
                    std::cout << ' ';
                }
            }
            std::cout << "|\n"; // Dešinė riba

        }
        std::cout << std::string(SCREEN_WIDTH + 2, '-') << '\n';
    }

void checkBulletAlienCollisions(GameState& gameState) {
    if (!gameState.bulletActive) {
        return;
    }

    for (auto& row : gameState.alienRows) {
        for (auto& alien : row.aliens) {

            if (alien.alive && gameState.bullet.x == alien.x && gameState.bullet.y == alien.y) {
                alien.alive = false;
                gameState.bulletActive = false;
                gameState.score += 20;  
                return; 
            }
        }
    }

    for (auto& row : gameState.shooting_alienRows) {
        for (auto& alien : row.aliens) {         
            if (alien.alive && gameState.bullet.x == alien.x && gameState.bullet.y == alien.y) {
                alien.alive = false;
                gameState.bulletActive = false;
                gameState.score += 30;  
                return; 
            }
        }
    }
    for (auto& row : gameState.tank_alienRows) {
        for (auto& alien : row.aliens) {
            if (alien.alive && gameState.bullet.x == alien.x && gameState.bullet.y == alien.y) {
                alien.alive = false;
                gameState.bulletActive = false;
                gameState.score += 10;  
                return; 
            }
        }
    }
}


int main() {
    GameState gameState; 
    bool gameRunning = true;
    
    while (gameRunning) {

        if (_kbhit()) {
            char ch = _getch();
            if (ch == 'a') {
                gameState.player.moveLeft();
            }
            else if (ch == 'd') {
                gameState.player.moveRight();
            }
            else if (ch == 'w' ) {
                if (!gameState.bulletActive) {
                    gameState.bullet = Bullet(gameState.player.x, gameState.player.y - 1);
                    gameState.bulletActive = true;
                }
            }
            else if (ch == 'q') {
                gameRunning = false;
            }
        }
        for (auto& row : gameState.shooting_alienRows) { 
            row.move(); 
        }
        for (auto& row : gameState.alienRows) {
            row.move(); 
        }
        for (auto& row : gameState.tank_alienRows) {
            row.move(); 
        }

        if (gameState.bulletActive) {
            gameState.bullet.move();
            if (!gameState.bullet.active) {
                gameState.bulletActive = false;
            }
        }
        if (checkPlayerAlienCollision(gameState.player, gameState.shooting_alienRows, gameState.alienRows, gameState.tank_alienRows)) {
            break;
        }
        if (gameState.areAllAliensDead()) {
        // 
            break;
        }
        gameState.checkAlienBunkerCollisions();
        gameState.checkBulletBunkerCollision();
        checkBulletAlienCollisions(gameState);
        drawGameField(gameState);

        std::this_thread::sleep_for(std::chrono::milliseconds(GAME_SPEED));
    }

    std::cout << "Žaidimas baigtas! Jūsų rezultatas: " << gameState.score << std::endl;
    return 0;
}



