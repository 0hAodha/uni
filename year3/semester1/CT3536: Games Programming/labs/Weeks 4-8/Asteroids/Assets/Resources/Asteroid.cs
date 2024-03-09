using System.Collections; 
using System.Collections.Generic; 
using UnityEngine; 
 
public class Asteroid : MonoBehaviour { 
 
    // inspector settings 
    public Rigidbody rigidBody; 
    // 
 
    // Use this for initialization 
    void Start () { 
        // randomise velocity 
        rigidBody.velocity = new Vector3(Random.Range(-10f,10f), 0f, Random.Range (-10f, 10f)); 
        rigidBody.angularVelocity = new Vector3(Random.Range(-4f,4f), Random.Range (-4f, 4f), Random.Range (-4f, 4f)); 
    } 
 
    public void SetScale(float min, float max) { 
        transform.localScale = new Vector3(Random.Range(min,max), Random.Range(min,max), Random.Range(min,max)); 
        rigidBody.mass = transform.localScale.x * transform.localScale.y * transform.localScale.z; 
    } 
 
    void OnCollisionEnter(Collision collision) { 
        if (!collision.gameObject.name.Contains("asteroid")) { 
            Spaceship ss = collision.gameObject.GetComponent<Spaceship> (); 
            if (ss != null && ss.isInvulnerable) 
                return; 

            if (collision.gameObject.name.Contains("Bullet")) {
                GameManager.currentScore = GameManager.currentScore + 10;     // assuming same points no matter how big asteroid is
            }
 
            // we've collided with something other than another asteroid 
            Destroy(collision.gameObject); // if it's the player spaceship, the Spaceship script’s OnDestroy will look after re-creating it 
            Destroy(this.gameObject); 
 
            if (rigidBody.mass > 0.00015f) { 
                float minScale = rigidBody.mass * 50f; 
                float maxScale = minScale * 2f; 
                for (int i = 0; i < 3; i++) { 
                    GameObject go = Instantiate (GameManager.instance.asteroidPrefab) as GameObject; 
                    go.transform.position = transform.position; 
                    go.GetComponent<Asteroid> ().SetScale (minScale, maxScale); 
                } 
            } 

            // if there are no more asteroids left, start next level 
            if (GameObject.FindGameObjectsWithTag("asteroid").Length == 0) {
                GameManager.StartNextLevel();
            }
        } 
    } 
}  