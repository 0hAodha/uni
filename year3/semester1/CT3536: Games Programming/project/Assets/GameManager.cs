using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class GameManager : MonoBehaviour
{
    public TextMeshProUGUI textMesh;
    public GameObject playerCar;
    public Vector3 startingPosition;
    public static bool isRaceOn = false;
    public AudioClip buzzer;
    public AudioClip airHorn;
    private static AudioSource audioSource;

    void Start()
    {
        Instantiate(playerCar, startingPosition, Quaternion.identity);

        audioSource = gameObject.AddComponent<AudioSource>();
        audioSource.clip = buzzer;

        StartCoroutine(Countdown());
    }

    void Update() {
        // escape to main menu
        if (Input.GetKeyDown(KeyCode.Escape)) {
            isRaceOn = false;
            SceneManager.LoadScene(0);
        } 
    }

    // countdown for race to start
    IEnumerator Countdown() {
        WaitForSeconds onesec = new WaitForSeconds(1);  // reuse waitforseconds for efficiency

        for (int i = 3; i > 0; i--) {
            // play horn noise for each pip
            audioSource.Play();
            yield return onesec;
        }

        // switch audio to airhorn and play to signify go
        audioSource.clip = airHorn;
        audioSource.Play();

        isRaceOn = true;
    }

    // static method for one of the cars to call when they have completed 3 laps
    public static void EndRace(string winner) {
        isRaceOn = false;

        // if winner is the player, load the win screen
        if (winner == "Player") {
            SceneManager.LoadScene(2);
        }
        // else load the lose screen
        else {
            SceneManager.LoadScene(3);
        }
    }
}
