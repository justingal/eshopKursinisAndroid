using System.Collections.Generic;
using UnityEngine;

public class GameController : MonoBehaviour
{
    public Player playerPrefab; // Nuoroda į žaidėjo prefabą
    public List<Player> playersList = new List<Player>(); // Žaidėjų sąrašas

    public void CreatePlayer(string playerName)
    {
        Player newPlayer = Instantiate(playerPrefab, new Vector3(0, 0, 0), Quaternion.identity);
        newPlayer.Initialize(playerName);
        playersList.Add(newPlayer);
        UpdateScoreUI(); // Čia galite iškviesti UI atnaujinimo funkciją
    }

    public void UpdateScoreUI()
    {
        // Čia atnaujinkite UI elementą su naujausiais taškais
        // Pvz., galite išvesti visų žaidėjų taškus
        foreach (var player in playersList)
        {
            Debug.Log($"{player.playerName} Score: {player.playerData.score}");
        }
    }

    public int GetTotalScore()
    {
        int totalScore = 0;
        foreach (Player player in playersList)
        {
            totalScore += player.GetComponent<PlayerData>().score;
        }
        return totalScore;
    }

    public void RemovePlayer(Player player)
    {
        playersList.Remove(player);
        Destroy(player.gameObject); // Papildyta sunaikinimo logika
    }
}
