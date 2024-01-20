using UnityEngine.SceneManagement;
using UnityEngine;

public class Invaders : MonoBehaviour
{
    public Invader[] prefabs;
    public int rows = 5;
    public int cols = 11;
    public AnimationCurve speed ;
    public Projectile missileprefab;
    public float missileAttackRate = 1.0f;
    public int ammontAlive => this.totalInvaders - this.ammountKilled;
    public int ammountKilled { get; private set; }
    public int totalInvaders => this.rows * this.cols;
    public float percentKilled => (float) this.ammountKilled / (float) this.totalInvaders;

    public Vector3 direction { get; private set; } = Vector3.right;
    public Vector3 initialPosition { get; private set; }
    private void Awake()
    {
        for (int row = 0; row < this.rows; row++)
        {
            float width = 2.0f * (this.cols - 1);
            float height = 2.0f * (this.rows - 1);
            Vector2 centering = new Vector2(-width / 2, -height / 2);
            Vector3 rowPosition = new Vector3(centering.x, centering.y+ ( row * 2),  0.0f);
            for (int col = 0; col < this.cols; col++)
            {
                if (col % 2 == 0)
                {
                    
                    Invader invader = Instantiate(this.prefabs[row], this.transform);
                    invader.killed += InvaderKilled;
                    Vector3 position = rowPosition;
                    position.x += col * 1.5f;
                    invader.transform.localPosition = position;
                }

            }
        }
    }

    private void Start()
    {
        InvokeRepeating(nameof(MissileAttack), this.missileAttackRate, 1 );
    }
    private void Update()
    {
        this.transform.position += direction * speed.Evaluate(this.percentKilled) * Time.deltaTime ;
        Vector3 leftEdge = Camera.main.ViewportToWorldPoint(Vector3.zero);
        Vector3 rightEdge = Camera.main.ViewportToWorldPoint(Vector3.right);
        foreach (Transform invader in transform)
        {
            // Skip any invaders that have been killed
            if (!invader.gameObject.activeInHierarchy)
            {
                continue;
            }

            // Check the left edge or right edge based on the current direction
            if (direction == Vector3.right && invader.position.x >= (rightEdge.x - 1f))
            {
                AdvanceRow();
                break;
            }
            else if (direction == Vector3.left && invader.position.x <= (leftEdge.x + 1f))
            {
                AdvanceRow();
                break;
            }
        }
    }
    private void AdvanceRow()
    {
        // Flip the direction the invaders are moving
        direction = new Vector3(-direction.x, 0f, 0f);

        // Move the entire grid of invaders down a row
        Vector3 position = transform.position;
        position.y -= 1f;
        transform.position = position;
    }
    private void MissileAttack()
    {
        foreach (Transform invader in this.transform)
        {
            if (!invader.gameObject.activeInHierarchy)
            {
                continue;
            }
            if ( Random.value < (3.0f / (float)this.ammontAlive))
            {
                Instantiate(this.missileprefab, invader.position, Quaternion.identity);
                break;
            }
        }
    }
    private void InvaderKilled()
    {
        this.ammountKilled++;
        if (this.ammountKilled >= this.totalInvaders) 
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
        }
    }
}
