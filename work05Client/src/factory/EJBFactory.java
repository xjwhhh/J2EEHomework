package factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 *
 * stateless beans的路径ejb:/<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>：
 module-name为server build得到的.jar文件名字（不包含”.jar”）；
 app-name和distinct-name均为空；
 bean-name为bean名字，只有名字；
 fully-qualified-classname-of-the-remote-interface为interface路径+名称。
 */
public class EJBFactory {
    public static Object getEJB(String beanName, String interfaceName) {
        Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try {
            Context context = new InitialContext(jndiProperties);
            final String fullName = "ejb://Server_ejb//" + beanName + "!" + interfaceName;
            return context.lookup(fullName);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
