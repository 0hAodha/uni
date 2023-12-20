-- 1. List all players playing for a given team
-- SELECT * FROM active_players JOIN players ON active_players.player_id = players.player_id WHERE team_name = "<insert team name here>" AND time = CURRENT_TIMESTAMP;
SELECT player_name FROM players WHERE team_name = "man u";

-- 2. List all players who have scored in a given game
SELECT player_id FROM goals WHERE game_id = "<insert game ID here>";
SELECT player_name FROM goals JOIN players ON goals.player_id = players.player_id WHERE game_id = 1;

-- 3. List the top five goal scorers in the league
SELECT player_id, COUNT(*) as goals_scored FROM goals GROUP BY player_id ORDER BY goals_scored DESC LIMIT 5;
SELECT player_id, COUNT(*) as goals_scored FROM goals GROUP BY player_id ORDER BY goals_scored DESC LIMIT 5;
SELECT
    player_name, COUNT(*) as goals_scored
FROM
    goals INNER JOIN players ON goals.player_id = players.player_id
GROUP BY players.player_id
ORDER BY goals_scored DESC
LIMIT 5;

-- 4. List all teams and the amounts of points that they have so far
-- drawback: adding up each time, costly if this is a common query
SELECT
    teams.team_name,
    SUM(
        CASE
            WHEN results.winner = teams.team_name THEN 3
            WHEN results.winner IS NULL THEN 1
            ELSE 0
        END
    ) AS total_points
FROM
    teams
LEFT JOIN games ON teams.team_name = games.home_team OR teams.team_name = games.away_team
LEFT JOIN results ON games.game_id = results.game_id
GROUP BY
    teams.team_name;
