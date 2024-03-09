using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpeedLimiter : MonoBehaviour { 
 
    // inspector settings 
    public Rigidbody rigid; 
    public float speedLimit = 5f; 
    // 
     
    // Update is called once per frame 
    void FixedUpdate () { 
        float spd = rigid.velocity.magnitude; 
        if (spd > speedLimit) 
            rigid.velocity *= speedLimit / spd;  
    } 
}