using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    public GameObject bullet;
    public float speed = 20f;

    // Start is called before the first frame update
    void Start()
    {
        // set the bullet moving
        bullet.GetComponent<Rigidbody>().velocity = bullet.transform.forward * speed;

        // start periodically checking for being off-screen 
        InvokeRepeating ("CheckScreenEdges", 0.2f, 0.2f); 
    }

    // Update is called once per frame
    void Update()
    {

    }

    private void CheckScreenEdges() { 
        Vector3 pos = bullet.transform.position;   
        Vector3 vel = bullet.GetComponent<Rigidbody>().velocity;   

        if (pos.x < GameManager.screenBottomLeft.x || pos.x > GameManager.screenTopRight.x || pos.z < GameManager.screenBottomLeft.z || pos.z > GameManager.screenTopRight.z) {
            Destroy(bullet);
        }
    } 
}
