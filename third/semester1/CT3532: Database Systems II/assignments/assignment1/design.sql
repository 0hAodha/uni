CREATE TABLE teams (
    team_name VARCHAR(255) NOT NULL,        -- assuming team name is unique
    home_venue VARCHAR(255) NOT NULL,       -- assuming that two teams could share a home venue (e.g, A teams and B teams)
    manager VARCHAR(255),                   -- assuming no other information is known/required for managers other than name

    PRIMARY KEY (team_name)
);

CREATE TABLE players (
    player_id INT NOT NULL AUTO_INCREMENT,
    team_name VARCHAR(255) NOT NULL,
    squad_number INT NOT NULL,              -- assuming squad number is unique within squads
    player_name VARCHAR(255) NOT NULL,

    PRIMARY KEY (player_id),
    FOREIGN KEY (team_name) REFERENCES teams(team_name)
);

CREATE TABLE games (
    game_id INT NOT NULL AUTO_INCREMENT,    -- no other uniquely identifying information about a game (there could be two otherwise identical games)
    home_team VARCHAR(255) NOT NULL,        -- venue can be inferred from this
    away_team VARCHAR(255) NOT NULL,
    path_to_heap_file VARCHAR(255), 

    PRIMARY KEY (game_id),
    FOREIGN KEY (home_team) REFERENCES teams(team_name),
    FOREIGN KEY (away_team) REFERENCES teams(team_name)
);

-- this table should only be inserted into upon the completion of a game
CREATE TABLE results (
    game_id INT NOT NULL,
    winner VARCHAR(255),                    -- NULL value indicates a draw

    PRIMARY KEY (game_id),
    FOREIGN KEY (winner) REFERENCES teams(team_name)
);

-- data redundancy here: players rarely change during match
CREATE TABLE active_players (
    active_time TIMESTAMP NOT NULL,
    game_id INT NOT NULL,
    player_id INT NOT NULL,

    PRIMARY KEY (active_time, player_id),
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);

CREATE TABLE substitutions (
    substitution_time TIMESTAMP NOT NULL,
    game_id INT NOT NULL,
    off_player_id INT NOT NULL,
    on_player_id INT NOT NULL,

    PRIMARY KEY (substitution_time, off_player_id), -- assuming two substitutions could be done at the same time
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (off_player_id) REFERENCES players(player_id),
    FOREIGN KEY (on_player_id) REFERENCES players(player_id)
);

CREATE TABLE sendoffs (
    sendoff_time TIMESTAMP NOT NULL, 
    game_id INT NOT NULL,
    player_id INT NOT NULL,

    PRIMARY KEY (sendoff_time, player_id),  -- assuming two sendoffs could be done at the same time - otherwise could just use time
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (player_id) REFERENCES players(player_id)
);

CREATE TABLE goals (
    goal_time TIMESTAMP NOT NULL,           -- assuming two goals can't be scored at the same time
    game_id INT NOT NULL,
    player_id INT NOT NULL,                 -- cam infer squad number from this 
    benefitting_team VARCHAR(255) NOT NULL, -- the team to which the points are awarded for this goal

    PRIMARY KEY (goal_time, game_id),
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (player_id) REFERENCES players(player_id),
    FOREIGN KEY (benefitting_team) REFERENCES teams(team_name)
);
