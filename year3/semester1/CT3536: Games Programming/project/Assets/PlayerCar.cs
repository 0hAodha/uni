using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// player car controller
public class PlayerCar : MonoBehaviour
{
    private float hInput;
    private float vInput;

    public float engineStrength = 60000;
    public float maxSteerAngle = 90;

    public Transform frontLeftWheelTransform;
    public Transform frontRightWheelTransform;
    public Transform rearLeftWheelTransform;
    public Transform rearRightWheelTransform;

    public WheelCollider frontLeftWheelCollider;
    public WheelCollider frontRightWheelCollider;
    public WheelCollider rearLeftWheelCollider;
    public WheelCollider rearRightWheelCollider;

    private int lapCount = 0;

    private void FixedUpdate()
    {
        // only accept input if race has started 
        if (GameManager.isRaceOn) {
            hInput = Input.GetAxis("Horizontal");
            vInput = Input.GetAxis("Vertical");

            // front wheel drive
            frontLeftWheelCollider.motorTorque = vInput * engineStrength;
            frontRightWheelCollider.motorTorque = vInput * engineStrength;

            // steering
            frontLeftWheelCollider.steerAngle = maxSteerAngle * hInput;
            frontRightWheelCollider.steerAngle = maxSteerAngle * hInput;

            // rotate wheels to propel car
            Vector3 pos;
            Quaternion rot;

            frontLeftWheelCollider.GetWorldPose(out pos, out rot);
            frontLeftWheelTransform.rotation = rot;
            frontLeftWheelTransform.position = pos;

            frontRightWheelCollider.GetWorldPose(out pos, out rot);
            frontRightWheelTransform.rotation = rot;
            frontRightWheelTransform.position = pos;

            rearLeftWheelCollider.GetWorldPose(out pos, out rot);
            rearLeftWheelTransform.rotation = rot;
            rearLeftWheelTransform.position = pos;

            rearRightWheelCollider.GetWorldPose(out pos, out rot);
            rearRightWheelTransform.rotation = rot;
            rearRightWheelTransform.position = pos;
        }
    }


    private void OnTriggerEnter(Collider other) {
        // if collided with finish line
        if (other.gameObject.CompareTag("FinishLine")) {
            lapCount++;

            // if finished laps and race not over, declare victory
            if (lapCount > 3 && GameManager.isRaceOn) {
                GameManager.EndRace("Player");
            }
            // else update lap counter display
            else {
                LapDisplay.IncrementCount();
            }
        }
    }
}
