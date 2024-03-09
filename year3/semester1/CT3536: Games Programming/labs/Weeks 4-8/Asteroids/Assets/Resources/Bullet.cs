using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour { 
 
    // inspector settings 
    public Rigidbody rigid; 
    // 
 
    // Use this for initialization 
    void Start () { 
        rigid.velocity = transform.forward * 30f; 
    } 
     
    // Update is called once per frame 
    void Update () { 
         
    } 
}  