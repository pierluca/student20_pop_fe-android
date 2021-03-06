package com.github.dedis.student20_pop.utility.security;

import com.github.dedis.student20_pop.model.Keys;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class SignatureTest {

  private final String privateKey = new Keys().getPrivateKey();
  private final String data = Hash.hash("Data to sign");

  @Test
  public void signNullKeysTest() {
    assertThrows(IllegalArgumentException.class, () -> Signature.sign((String) null, data));
    assertThrows(
        IllegalArgumentException.class, () -> Signature.sign((ArrayList<String>) null, data));
  }

  @Test
  public void signNullDataTest() {
    assertThrows(IllegalArgumentException.class, () -> Signature.sign(privateKey, null));
  }

  @Test
  public void signTest() {
    assertNotNull(Signature.sign(privateKey, data));
  }
}
