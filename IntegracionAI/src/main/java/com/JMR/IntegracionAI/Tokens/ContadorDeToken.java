package com.JMR.IntegracionAI.Tokens;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;

public class ContadorDeToken {
      public int Contador(String system, String user) {
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
        return enc.countTokens(system + user);

    }
}
