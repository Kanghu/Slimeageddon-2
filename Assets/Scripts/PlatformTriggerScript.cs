using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlatformTriggerScript : MonoBehaviour {

    public Collider2D myCollider;

	// Use this for initialization
	void Start () {
     //   myCollider = GetComponentInParent<Collider2D>();
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnTriggerEnter2D(Collider2D collision)
    {

        if (collision.gameObject.CompareTag("Player"))
        {
            Physics2D.IgnoreCollision(myCollider, collision.gameObject.GetComponent<Collider2D>(), true);
        }
    }

    void OnTriggerExit2D(Collider2D collision)
    {
        Physics2D.IgnoreCollision(myCollider, collision, false);
    }
}
