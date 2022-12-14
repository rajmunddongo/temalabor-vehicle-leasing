package hu.bme.aut.temalab.temalaborvehicleleasing.controller.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.aut.temalab.temalaborvehicleleasing.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CustomAuthorityDeserializerForCustomer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

        Iterator<JsonNode> elements = jsonNode.elements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            if (authority != null) {
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
            } else {
                grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ROLE_CUSTOMER.toString()));
            }
        }
        return grantedAuthorities;
    }
}

