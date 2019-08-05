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
        swirling_axe.GetComponent<Viking_swirlingAxe>().playerTransform = transform;
        swirling_axe.SetActive(false);
    }





    // Update is called once per frame
    void Update()
    {

        if (!isLocalPlayer)
        {
            return;
        }



        if (Input.GetKeyDown(KeyCode.Q) && !playerScript.immovable)
        {
            if ((Time.time > CD.Start_Q + CD.Cooldown_Q || CD.Start_Q == 0f) && (playerScript.mana > stunMana))
            {
                anim.SetBool("stun", true);

                CD.Start_Q = Time.time;
                playerScript.mana -= stunMana;

                stun_collider.SetActive(true);

                Invoke("DisableCollider", 0.416f);

            }
        }

        if (Input.GetButtonDown("Fire2") && !playerScript.immovable)
        {
            float ofset;


            if (playerScript.faceRight)
                ofset = 0.5f;
            else
                ofset = -0.5f;



            if (hasAxe)
            {

                if ((Time.time > CD.Start_M2 + CD.Cooldown_M2 || CD.Start_M2 == 0f) && (playerScript.mana > whirleMana))
                {
                    CD.Start_M2 = Time.time;
                    playerScript.mana -= whirleMana;

                    if (isServer)
                    {
                        RpcWhirle(ofset);
                    }

                    else
                    {
                        

                        hasAxe = false;

                        swirling_axe.SetActive(true);
                        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = false;
                        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = playerScript.faceRight;
                        swirling_axe.GetComponent<Viking_swirlingAxe>().playerTransform = GetComponent<Transform>();
                        swirling_axe.transform.localPosition = new Vector3(transform.position.x + ofset, transform.position.y + 0.18f, transform.position.z);

                        CmdWhirle(ofset);
                    }

                    swirling_axe.GetComponentInChildren<Camera>().enabled = true;
                }
            }

            else
            {
                if (!swirling_axe.GetComponent<Viking_swirlingAxe>().returning)
                {
                    if (isServer)
                    {
                        RpcCome();
                    }

                    else
                    {
                        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = !swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight;
                        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = true;

                        CmdCome();
                    }
                }
            }

        }
    }

    void DisableCollider()
    {
        stun_collider.SetActive(false);
        anim.SetBool("stun", false);
    }


    [Command]
    public void CmdCome()
    {
        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = !swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight;
        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = true;
    }

    [Command]
    public void CmdWhirle(float ofset)
    {
        

        swirling_axe.SetActive(true);
        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = false;
        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = playerScript.faceRight;
        swirling_axe.GetComponent<Viking_swirlingAxe>().playerTransform = GetComponent<Transform>();
        swirling_axe.transform.localPosition = new Vector3(transform.position.x + ofset, transform.position.y + 0.18f, transform.position.z);
    }

    [ClientRpc]
    public void RpcWhirle(float ofset)
    {
       

        swirling_axe.SetActive(true);
        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = false;
        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = playerScript.faceRight;
        swirling_axe.GetComponent<Viking_swirlingAxe>().playerTransform = GetComponent<Transform>();
        swirling_axe.transform.localPosition = new Vector3(transform.position.x + ofset, transform.position.y + 0.18f, transform.position.z);
    }

    [ClientRpc]
    public void RpcCome()
    {
        swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight = !swirling_axe.GetComponent<Viking_swirlingAxe>().faceRight;
        swirling_axe.GetComponent<Viking_swirlingAxe>().returning = true;
    }

}
