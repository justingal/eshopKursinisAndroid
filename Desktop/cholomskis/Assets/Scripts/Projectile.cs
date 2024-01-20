
using UnityEngine;

public class Projectile : MonoBehaviour
{
    public Vector3 direction;
    public float speed;
    public event System.Action destroyed;

    private void Update()
    {
        this.transform.position += direction * this.speed * Time.deltaTime;
    }
    private void OnTriggerEnter2D(Collider2D other)
    {   
        if (this.destroyed!= null){ 
        this.destroyed.Invoke();
        }
        
        Destroy(this.gameObject);
    }
}
