using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GUIScript : MonoBehaviour
{

    public Canvas menu;
    public Canvas gameCanvas;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void OnButtonClick() {
        menu.gameObject.SetActive(false);
        gameCanvas.gameObject.SetActive(true);
        
        GameManager.inMenuState = false;
        GameManager.StartNewGame();
    }

    public void SwitchToMenu() {
        menu.gameObject.SetActive(true);
        gameCanvas.gameObject.SetActive(false);
        
        GameManager.inMenuState = true;
    }
}
