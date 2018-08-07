using UnityEngine;
using UnityEngine.Networking;

public class classViking : NetworkBehaviour {

    //Player Characteristics

    PlayerController playerScript;
    Animator anim;
    Transform PlayerRect;
    Cooldowns CD;   // To Be Implemented

    public bool hasAxe = true;
    public GameObject swirling_axe;
    //public Animator weapon_hitbox;
    //public GameObject weapon_object;
    public GameObject stun_collider;

    
    public int stunMana = 5;
    public int tpMana = 5;
    public int whirleMana = 5;




    // Use this for initialization
    void Start () {

        playerScript = GetComponent<PlayerController>();
        anim = GetComponent<Animator>();
        PlayerRect = GetComponent<Transform>();
        CD = GetComponent<Cooldowns>();

        //Instantiate Axe
        swirling_axe = Instantiate(swirling_axe, transform.position, Quaternion.Euler(0, 0, 0)) as GameObject;
        swirling_axe.SetActive(false);
    }
	




	// Update is called once per frame
	void Update () {

        if (!isLocalPlayer)
        {
            return;
        }


        if (Input.GetKeyDown(KeyCode.Q) && !playerScript.immovable)
        {
            if ((Time.time > CD.Start_Q + CD.Cooldown_Q || CD.Start_Q == 0f) && (playerScript.mana > stunMana))
            {
                CD.Start_Q = Time.time;
                playerScript.mana -= stunMana;

                stun_collider.SetActive(true);

                Invoke("DisableCollider", 0.416f);
                
            }
        }

    }

    void DisableCollider()
    {
        stun_collider.SetActive(false);
    }


}
