using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class GameManager : MonoBehaviour { 
 
    // inspector settings 
    public GameObject asteroidPrefab, spaceshipPrefab;
    public TextMeshProUGUI scoreText;

    // class-level statics 
    public static GameManager instance; 
    public static int currentGameLevel; 
    public static Vector3 screenBottomLeft, screenTopRight; 
    public static float screenWidth, screenHeight; 

    public static bool inMenuState = true;
    public static int highScore = 0;
    public static int numLives = 3;    
    public static int currentScore = 0;
 
    // Use this for initialization 
    void Start () { 
        instance = this; 
        Camera.main.transform.position = new Vector3 (0f, 30f, 0f); 
        Camera.main.transform.LookAt (Vector3.zero, new Vector3 (0f, 0f, 1f)); 
        currentGameLevel = 0; 
    } 

    // probably inefficient to update it so often but this works and i'm lazy
    // would be better to only update the text when the values are updated, via a method that updates both
    void Update() {
        scoreText.text = "High Score: " + highScore + " Score: " + currentScore + " Lives: " + numLives;
    }

    public static void StartNewGame() {
        numLives = 3;
        StartNextLevel (); 
        CreatePlayerSpaceship (); 
    }
 
    public static void StartNextLevel() { 
        currentGameLevel++; 
 
        // find screen corners and size, in world coordinates 
        // for ViewportToWorldPoint, the z value specified is in world units from the camera 
        screenBottomLeft = Camera.main.ViewportToWorldPoint(new Vector3(0f,0f,30f)); 
        screenTopRight = Camera.main.ViewportToWorldPoint (new Vector3(1f,1f,30f)); 
        screenWidth = screenTopRight.x - screenBottomLeft.x; 
        screenHeight = screenTopRight.z - screenBottomLeft.z; 
 
        // instantiate some asteroids near the edges of the screen 
        for (int i = 0; i < currentGameLevel * 2 + 3; i++) { 
            GameObject go = Instantiate (instance.asteroidPrefab) as GameObject; 
            float x, z; 
            if (Random.Range (0f, 1f) < 0.5f) 
                x = screenBottomLeft.x + Random.Range (0f, 0.15f) * screenWidth; // near the left edge 
            else 
                x = screenTopRight.x - Random.Range (0f, 0.15f) * screenWidth; // near the right edge 
            if (Random.Range (0f, 1f) < 0.5f) 
                z = screenBottomLeft.z + Random.Range (0f, 0.15f) * screenHeight; // near the bottom edge 
            else 
                z = screenTopRight.z - Random.Range (0f, 0.15f) * screenHeight; // near the top edge 
            go.transform.position = new Vector3(x, 0f, z); 
            go.GetComponent<Asteroid> ().SetScale (0.08f, 0.12f); 
        } 
 
    } 
 
    public static void CreatePlayerSpaceship() { 
        // instantiate the player's spaceship 
        GameObject go = Instantiate (instance.spaceshipPrefab) as GameObject; 
        go.transform.position = Vector3.zero; 
    } 
}