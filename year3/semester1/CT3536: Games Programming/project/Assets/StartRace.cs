using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

// script to enable starting the race from the main menu
public class StartRace : MonoBehaviour
{
    public void StartRaceScene() {
        SceneManager.LoadScene(1);
    }
}
