// Andrew Hayes, ID: 21321503
// some of the comments here are quite obvious, and are just here for my own learning purposes
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour {
    // inspector settings
    public GameObject mars;
    public GameObject phobos; 
    public GameObject deimos;
    public GameObject asteroid;

    // speed that the camera moves around mars on arrow keypress
    public float cameraSpeed = 500;

    // Start is called before the first frame update
    void Start() {
        // set position of mars object and point camera at it
        mars.transform.position = new Vector3(0,0,0);
        mars.transform.rotation = Quaternion.Euler(new Vector3(270,0,0)); // make it so mars' north pole points up
        Camera.main.transform.position = new Vector3(0,0,-100);
        Camera.main.transform.LookAt(mars.transform);

        // before this can run, you need to manually add a rigid body with 0 angular velocity and no gravity in the UI
        // start mars rotating 
        mars.GetComponent<Rigidbody>().AddTorque(new Vector3(0,20,0));
    }

    void Update() {
        // rotate phobos and deimos a little each frame 
        phobos.transform.RotateAround(mars.transform.position, Vector3.up, 32*Time.deltaTime);
        deimos.transform.RotateAround(mars.transform.position, Vector3.up, 8*Time.deltaTime);

        // control the camera's position using the arrow keys
        if (Input.GetKey(KeyCode.LeftArrow)) {
            Camera.main.transform.RotateAround(Vector3.zero, Vector3.up, cameraSpeed * Time.deltaTime);
        }
        else if (Input.GetKey(KeyCode.RightArrow)) { 
            Camera.main.transform.RotateAround(Vector3.zero, Vector3.up, -cameraSpeed * Time.deltaTime);
        }
        else if (Input.GetKey(KeyCode.UpArrow)) {
            Camera.main.transform.RotateAround(Vector3.zero, Vector3.right, cameraSpeed * Time.deltaTime);
        }
        else if (Input.GetKey(KeyCode.DownArrow)) {
            Camera.main.transform.RotateAround(Vector3.zero, Vector3.right, -cameraSpeed * Time.deltaTime);
        }

        // randomly spawn new asteroids
        if (Random.Range(1,180) == 1) { // assuming Update() is called 60 times per second, want to spawn a new asteroid on average once every 3 seconds
            Instantiate(asteroid);     // instantiating asteroid prefab
        }
    }
}

