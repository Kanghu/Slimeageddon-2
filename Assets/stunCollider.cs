using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class stunCollider : MonoBehaviour {


    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.tag == "Player 1")
        {
            collision.gameObject.GetComponent<PlayerController>().Stun(GetComponentInParent<Cooldowns>().Duration_Q); //Stun the colliding player for 'CD.Duration_Q' sec
        }
    }
}
