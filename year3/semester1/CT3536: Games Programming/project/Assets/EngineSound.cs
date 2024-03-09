using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// script to control the sound of the car's engine
public class EngineSound : MonoBehaviour
{
    public AudioClip engineClip;
    public float minPitch = 0.8f;
    public float maxPitch = 1.2f;
    public float minVolume = 0.5f;
    public float maxVolume = 1.0f;
    private AudioSource audioSource;

    void Start()
    {
        audioSource = gameObject.AddComponent<AudioSource>();
        audioSource.clip = engineClip;
        audioSource.loop = true;
        audioSource.Play();
    }

    void Update()
    {
        float speed = Mathf.Abs(Input.GetAxis("Vertical"));
        
        float pitch = Mathf.Lerp(minPitch, maxPitch, speed);
        float volume = Mathf.Lerp(minVolume, maxVolume, speed);

        audioSource.pitch = pitch;
        audioSource.volume = volume;
    }
}
