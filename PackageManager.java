import java.util.ArrayList;

public class PackageManager {

    private ArrayList<Package> packages;

    public PackageManager() {

        packages = new ArrayList<>();
    }

    public void addPackage(Package p) {

        packages.add(p);
    }

    public ArrayList<Package> getPackages() {

        return packages;
    }

    public Package findPackage(String id) {

        for (Package p : packages) {

            if (p.getPackageId()
                    .equalsIgnoreCase(id)) {

                return p;
            }
        }

        return null;
    }

    public boolean deletePackage(String id) {

        Package p = findPackage(id);

        if (p != null) {

            packages.remove(p);

            return true;
        }

        return false;
    }
}