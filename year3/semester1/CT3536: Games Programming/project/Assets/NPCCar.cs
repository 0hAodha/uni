using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// car controller for the NPC car
public class NPCCar : MonoBehaviour
{
    // wheel transforms and colliders for driving animation
    public Transform frontLeftWheelTransform;
    public Transform frontRightWheelTransform;
    public Transform rearLeftWheelTransform;
    public Transform rearRightWheelTransform;

    public WheelCollider frontLeftWheelCollider;
    public WheelCollider frontRightWheelCollider;
    public WheelCollider rearLeftWheelCollider;
    public WheelCollider rearRightWheelCollider;

    private int lapCount = 0;

    // array of waypoints for car to navigate track
    public Transform[] waypoints;

    public float speed = 30f;
    public float rotationSpeed = 5f;

    private int waypoint = 0;   // next waypoint

    // variables for handling stopping if about to collide with player
    private Ray ray;            // ray to reuse for raycasting
    public float maxDistanceToPlayer = 15f;

    void Start() {
        ray = new Ray();
    }

    void Update()
    {
        if (GameManager.isRaceOn) {
            if (waypoint < waypoints.Length)
            {
                ray.origin = transform.position;
                ray.direction = transform.TransformDirection(Vector3.forward);

                // only proceed if route is not blocked by player
                if (Physics.Raycast(ray, out RaycastHit hit, maxDistanceToPlayer)) {
                    if (!hit.collider.CompareTag("Player")) {
                        DriveToNextWaypoint();
                    }
                    else {
                        Debug.Log("not proceeding because blocked by player");
                    }
                }
                else {
                    DriveToNextWaypoint();
                }
            }
            // if all waypoints have been visited, set next waypoint to the 0th one
            // keep doing circuit until race ends
            else
            {
                waypoint = 0;
            }
        }
    }

    void DriveToNextWaypoint()
    {
        // direction to next waypoint
        Vector3 direction = waypoints[waypoint].position - transform.position;
        direction.y = 0f;

        // look at next waypoint
        Quaternion rotation = Quaternion.LookRotation(direction);
        transform.rotation = Quaternion.Slerp(transform.rotation, rotation, rotationSpeed * Time.deltaTime);

        transform.Translate(Vector3.forward * speed * Time.deltaTime);

        // if within 3m of goal waypoint, move on to next one
        if (Vector3.Distance(transform.position, waypoints[waypoint].position) < 3f)
        {
            waypoint++;
        }

        // rotate wheels for driving animation
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

    private void OnTriggerEnter(Collider other) {
        // if collided with finish line
        if (other.gameObject.CompareTag("FinishLine")) {
            lapCount++;

            // if completed 3 laps and race is not finished, declare victory
            if (lapCount > 3 && GameManager.isRaceOn) {
                GameManager.EndRace("NPC");
            }
        }
    }
}
