using UnityEngine;

public class Invader : MonoBehaviour, IDamageable
{

    public int Health { get; set; }
    public Sprite[] animationSprites;
    public float animationTime = 1.00f;

    public event System.Action killed;

    private SpriteRenderer _spriteRenderer;
    private int _animationFrame;


    private void Awake()
    {
        _spriteRenderer = GetComponent<SpriteRenderer>();
    }
    private void Start()
    {
        InvokeRepeating(nameof(AnimateSprite), this.animationTime, this.animationTime);
    }
    public void TakeDamage(int damage)
    {
        Health -= damage;
        if (Health <= 0)
        {
            Destroy(gameObject);
            // Papildomai: praneškite apie invaderio sunaikinimą, pvz., naudodami įvykį.
        }
    }

    private void AnimateSprite()
    {
        _animationFrame++;

        if (_animationFrame >= this.animationSprites.Length)
        {
            _animationFrame = 0;
        }
        _spriteRenderer.sprite = this.animationSprites[_animationFrame];
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.layer == LayerMask.NameToLayer("Laser"))
        {
            this.killed.Invoke();
            this.gameObject.SetActive(false);
        }
    }

}