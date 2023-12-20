using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spaceship : MonoBehaviour
{
    public GameObject spaceship;
    public GameObject bullet;   // spawning bullets from the Spaceship class as they "belong" to the spaceship

    public float speed = 5.0f;
    public float rotationalSpeed = 2.0f;

    public float timeOfLastBullet;

    // Start is called before the first frame update
    void Start()
    {
        // start periodically checking for being off-screen 
        InvokeRepeating ("CheckScreenEdges", 0.2f, 0.2f); 
    }

    // Update is called once per frame
    void Update()
    {
        // move spaceship according to arrow keys
        // applying just a force to the spaceship object creates some unusual handling, but i feel that this is correct as in space there should be 0 drag, and if a force is applied in one direction, it should remain until it's cancelled out
        if (Input.GetKey(KeyCode.LeftArrow)) {
            spaceship.GetComponent<Rigidbody>().AddTorque(new Vector3(0, -rotationalSpeed, 0));
        }
        else if (Input.GetKey(KeyCode.RightArrow)) { 
            spaceship.GetComponent<Rigidbody>().AddTorque(new Vector3(0, rotationalSpeed, 0));
        }
        else if (Input.GetKey(KeyCode.UpArrow)) {
            spaceship.GetComponent<Rigidbody>().AddRelativeForce(new Vector3(0, 0, speed));
        }
        else if (Input.GetKey(KeyCode.DownArrow)) {
            spaceship.GetComponent<Rigidbody>().AddRelativeForce(new Vector3(0, 0, -speed));
        }

        // shoot a bullet
        if (Input.GetKeyUp(KeyCode.Space) && Time.time - timeOfLastBullet >= 0.25) {    // only spawning a bullet once the key is released and once 0.25 seconds has elapsed since the last bullet
            // spawning a bullet at the front tip of the spaceship 
            Instantiate(bullet, spaceship.transform.position + spaceship.transform.forward * spaceship.transform.localScale.z, spaceship.transform.rotation);
            timeOfLastBullet = Time.time;
        }
    }

    private void CheckScreenEdges() { 
        Vector3 pos = spaceship.transform.position;   
        Vector3 vel = spaceship.GetComponent<Rigidbody>().velocity;   
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
}
