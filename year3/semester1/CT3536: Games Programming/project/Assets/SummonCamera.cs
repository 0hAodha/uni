using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// script to summon the camera to the player car 
public class SummonCamera : MonoBehaviour
{
    public Vector3 offset = new Vector3(0f, 4f, -10f);
    public float speed = 10f; // speed of camera movement
    public Camera mc;

    void Start() {
        mc = Camera.main;
    }

    private void FixedUpdate()
    {
        mc.transform.position = Vector3.Lerp(mc.transform.position, transform.TransformPoint(offset), speed * Time.deltaTime);    
        mc.transform.rotation = Quaternion.Lerp(mc.transform.rotation, Quaternion.LookRotation(transform.position - mc.transform.position, Vector3.up), speed * Time.deltaTime);
    }
}
