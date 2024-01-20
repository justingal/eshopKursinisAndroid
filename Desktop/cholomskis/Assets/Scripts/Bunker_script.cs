using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bunker_script : MonoBehaviour, IDamageable //I damageable Interface
{
    public int Health { get; set; } = 5; 

    private void Start()
    {
        Health = 5; 
    }

    public void TakeDamage(int damage)
    {
        Health -= damage;
        if (Health == 0)
        {
            Destroy(gameObject); 
        }
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
 
        if (other.gameObject.layer == LayerMask.NameToLayer("Laser") || other.gameObject.layer == LayerMask.NameToLayer("Missile"))
        {
            TakeDamage(1);
        }

        
        if (other.gameObject.layer == LayerMask.NameToLayer("Invader"))
        {
            this.gameObject.SetActive(false);
        }
    }
}
