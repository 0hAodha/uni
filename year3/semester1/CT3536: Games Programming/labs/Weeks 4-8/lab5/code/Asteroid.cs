using System.Collections; 
using System.Collections.Generic; 
using UnityEngine; 
 
public class Asteroid : MonoBehaviour { 
 
    // inspector settings 
    public Rigidbody rigidBody; 
    public GameObject miniAsteroid;
 
    // Use this for initialization 
    void Start () { 
        // randomise size+mass 
        transform.localScale = new Vector3(Random.Range(0.06f,0.09f), Random.Range(0.06f,0.09f), Random.Range
(0.06f,0.09f)); 
        rigidBody.mass = transform.localScale.x * transform.localScale.y * transform.localScale.z; 
 
        // randomise velocity 
        rigidBody.velocity = new Vector3 (Random.Range (-20f, 20f), 0f, Random.Range (-20f, 20f)); 
        rigidBody.angularVelocity = new Vector3 (Random.Range (-20f, 20f), Random.Range (-
20f, 20f), Random.Range (-20f, 20f)); 
     
        // start periodically checking for being off-screen 
        InvokeRepeating ("CheckScreenEdges", 0.2f, 0.2f); 
    } 
     
    private void CheckScreenEdges() { 
        Vector3 pos = transform.position;   
        Vector3 vel = rigidBody.velocity;   
        float xTeleport = 0f, zTeleport = 0f; 
 
        if (pos.x < GameManager.screenBottomLeft.x && vel.x <= 0f) // velocity check as sanity test 
            xTeleport = GameManager.screenWidth; 
        else if (pos.x > GameManager.screenTopRight.x && vel.x >= 0f) 
            xTeleport = -GameManager.screenWidth; 
 
        if (pos.z < GameManager.screenBottomLeft.z && vel.z <= 0f) 
            zTeleport = GameManager.screenHeight; 
        else if (pos.z > GameManager.screenTopRight.z && vel.z >= 0f) 
            zTeleport = -GameManager.screenHeight; 
 
        if (xTeleport != 0f || zTeleport != 0f) 
            transform.position = new Vector3 (pos.x + xTeleport, 0f, pos.z + zTeleport); 
 
    } 

    // method to spawn mini-asteroid fragments at the contact point(s) of a collision
    private void OnCollisionEnter(Collision collision) {
        // Arraylist to keep track of the mini asteroids created for a collision
        ArrayList fragments = new ArrayList();

        foreach (ContactPoint contact in collision.contacts) {
            // instantiating a random number of mini asteroid between 1 and 5 inclusive
            int numFragments = Random.Range(1, 5);

            for (int i = 1; i <= numFragments; i++) {
                GameObject fragment = Instantiate(miniAsteroid);
                fragment.transform.position = contact.point;
                fragments.Add(fragment);
            }        
        }

        StartCoroutine(DestroyFragments(fragments));
    }

    // coroutine to destroy all the fragments from a collision 
    IEnumerator DestroyFragments(ArrayList fragments) {
        yield return new WaitForSeconds(3);

        foreach (GameObject fragment in fragments) {
            Destroy(fragment);
        }
    }
}