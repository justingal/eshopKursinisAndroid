using UnityEngine;
using UnityEngine.UI; // Reikalinga darbui su UI

public class UIManager : MonoBehaviour
{
    public Text scoreText; // Taškų tekstas
    public Text healthText; // Sveikatos tekstas
    public Text playerNameText; // Žaidėjo vardo tekstas

    public void UpdatePlayerUI(Player player)
    {
        scoreText.text = "Score: " + player.playerData.score;
        healthText.text = "Health: " + player.playerData.health;
        playerNameText.text = "Player: " + player.playerName;
    }


}
