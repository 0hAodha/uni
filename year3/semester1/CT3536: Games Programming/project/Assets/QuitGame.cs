using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// script to enable quitting the game from the main menu
public class QuitGame : MonoBehaviour
{
    public void QuitGameApplication() {
        Application.Quit();
    }
}
