using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using TMPro;

// script to handle updating the lap indicator in the UI
public class LapDisplay : MonoBehaviour
{
    public TextMeshProUGUI textMesh;
    private static int lapCount = 0;

    // static method to allow player car to update the lap count
    public static void IncrementCount() {
        lapCount++;
    }

    void Update() {
        textMesh.text = "Lap " + lapCount + "/3";
    }
}
