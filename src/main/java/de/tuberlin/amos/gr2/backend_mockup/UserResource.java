package de.tuberlin.amos.gr2.backend_mockup;

import com.atlassian.activeobjects.external.ActiveObjects;

// this class is used to store some code fragment, no exact use
public class UserResource {

    private ActiveObjects activeObjects;

    public UserResource(ActiveObjects activeObjects)
    {
        this.activeObjects = activeObjects;
    }

//    @POST
//    @Path("/addUser")
//    public Response addUser(@Context HttpServletRequest request, @FormParam("name") String name)
//    {
//        Users user = this.activeObjects.create();
//        user.setName(request.getParameter("name"));
//        user.save();
//
//        return Response.ok().build();
//    }

//    public void someMethod(final ActiveObjects ao)
//    {
//        ao.executeInTransaction(new TransactionCallback<Object>()
//        {
//            @Override
//            public Object doInTransaction()
//            {
//                // do something with AO
//                return null;
//            }
//        });
//    }


//        ao.executeInTransaction((TransactionCallback<Void>) () -> {
//            for (Users user : ao.find(Users.class, Query.select()))
//            {
//                w.printf("<li><%2$s> </li>", user.toString());
//            }
//            return null;
//        });


//        ao.executeInTransaction(() -> {
//            final Users user = ao.create(Users.class);
//            user.setName(name); // (3)
//            user.save(); // (4)
//            return user;
//        });

}
