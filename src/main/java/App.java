import factories.ServiceFactory;

import models.ModelAuto;
import models.ModelUser;
import services.Service;

import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        Service service = ServiceFactory.getInstance().getService();
        boolean registred = service.isRegistred("Vadim");
        System.out.println(registred);
        Map<Integer, ModelUser> users = service.getUsers();

        for(ModelUser u: users.values()){
            System.out.println(u);
            List<ModelAuto> autos = u.getAuto();
            if(autos != null && autos.size() > 0) {
                for (ModelAuto a : autos) {
                    System.out.println("    " + a);
                }
            }
        }

    }
}
