using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spaceship : MonoBehaviour { 
 
    // inspector settings 
    public Rigidbody rigidBody; 
    public GameObject bulletPrefab; 
    public GUIScript guiScript;

    // public member data 
    [HideInInspector] public bool isInvulnerable = true; 
    
 
    // private member data 
    private float lastFiredTime = 0f; 
    
 
    void Start() { 
        Invoke ("MakeVulnerable", 2f); 
    } 
 
    private void MakeVulnerable() { 
        isInvulnerable = false; 
    } 
 
    // Update is called once per frame 
    void FixedUpdate () { 
        if (Input.GetKey(KeyCode.UpArrow))  
        
        rigidBody.AddForce(transform.forward * (rigidBody.mass * Time.fixedDeltaTime * 500f)); 
        
        if (Input.GetKey(KeyCode.LeftArrow))  
            rigidBody.AddTorque(-transform.up * (rigidBody.mass * Time.deltaTime * 500f)); 
        
        else if (Input.GetKey(KeyCode.RightArrow))  
            rigidBody.AddTorque(transform.up * (rigidBody.mass * Time.deltaTime * 500f)); 
        
        // firing is only allowed at most once per 0.25 seconds 
        if (Input.GetKey (KeyCode.Space) && lastFiredTime + 0.25f <= Time.time) { 
            lastFiredTime = Time.time; 
            FireBullet (); 
        } 

        // methods for testing, should be removed for actual gameplay
        if (Input.GetKeyDown(KeyCode.Escape))
        {
            Destroy(gameObject);    // destroy spaceship
        }
        if (Input.GetKeyDown(KeyCode.Return))
        {
            // destroy all asteroids and start next level
            foreach (GameObject asteroid in GameObject.FindGameObjectsWithTag("asteroid")) {
                Destroy(asteroid);
                GameManager.StartNextLevel();

            }
        }
    } 
 
    void OnDestroy() {
        GameManager.numLives--;

        // if the spaceship has more lives, respawn
        if (GameManager.numLives > 0) {
            GameManager.CreatePlayerSpaceship(); 
        }
        // else destroy all asteroids and go to menu
        else {
            GameManager.highScore = GameManager.currentScore > GameManager.highScore ? GameManager.currentScore : GameManager.highScore;
            foreach (GameObject asteroid in GameObject.FindGameObjectsWithTag("asteroid")) {
                Destroy(asteroid);
            }
            guiScript.SwitchToMenu();
        }
    } 
 
    private void FireBullet() { 
        GameObject go = Instantiate(bulletPrefab); 
        go.transform.position = transform.position + transform.forward*3f; 
        go.transform.rotation = transform.rotation; 
    } 
}  