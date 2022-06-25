db.createUser(
        {
            user: "django",
            pwd: "django123",
            roles: [
                {
                    role: "readWrite",
                    db: "tpfinaldb"
                }
            ]
        }
);