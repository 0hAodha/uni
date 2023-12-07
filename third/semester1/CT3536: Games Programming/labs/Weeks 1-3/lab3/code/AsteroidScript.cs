using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AsteroidScript : MonoBehaviour
{
    public GameObject asteroid;

    // Start is called before the first frame update
    void Start()
    {
        // set asteroid start position to a random plae to the left of mars
        asteroid.transform.position = new Vector3(-500,Random.Range(-250, 250),Random.Range(-250,250));

        // adding force to the asteroir
        asteroid.GetComponent<Rigidbody>().AddForce(Vector3.right * 200000 * Time.deltaTime);
    }

    // Update is called once per frame
    void Update()
    {
        // destroy object if it goes off the right edge of the screen
        Vector3 position = Camera.main.WorldToScreenPoint(transform.position);
        if (position.x > Screen.width) {
            Destroy(asteroid);
        }
        
    }

    // destroy asterod upon collisions
    void OnCollisionEnter() {
        Destroy(asteroid);
    }
}
