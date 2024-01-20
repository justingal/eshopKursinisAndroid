using UnityEngine.SceneManagement;
using UnityEngine;

public class Player : MonoBehaviour, IDamageable
{
    public PlayerData playerData; // Nuoroda į PlayerData komponentą
    public Projectile laserPrefab;
    public float speed = 5.0f;
    private bool _laserActive;
    public string playerName;

    public void Initialize(string name)
    {
        playerName = name;
        // Čia galite pridėti kitą inicializavimo logiką
    }

    private void Awake()
    {
        playerData = GetComponent<PlayerData>();
    }

    public int Health { get => playerData.health; set => playerData.health = value; }

    void Update()
    {
        if (Input.GetKey(KeyCode.A) || Input.GetKey(KeyCode.LeftArrow))
        {
            transform.position += Vector3.left * speed * Time.deltaTime;
        }
        else if (Input.GetKey(KeyCode.D) || Input.GetKey(KeyCode.RightArrow))
        {
            transform.position += Vector3.right * speed * Time.deltaTime;
        }
        if (Input.GetKeyDown(KeyCode.W) || Input.GetKeyDown(KeyCode.UpArrow))
        {
            Shoot();
        }
    }

    public void TakeDamage(int damage)
    {
        Health -= damage;
        if (Health <= 0)
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
    }

    private void Shoot()
    {
        if (!_laserActive)
        {
            Projectile projectile = Instantiate(laserPrefab, transform.position, Quaternion.identity);
            projectile.destroyed += LaserDestroyed;
            _laserActive = true;
        }
    }

    private void LaserDestroyed()
    {
        _laserActive = false;
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.layer == LayerMask.NameToLayer("Missile"))
        {
            TakeDamage(1);
        }
        else if (other.gameObject.layer == LayerMask.NameToLayer("Invader"))
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
    }
}
