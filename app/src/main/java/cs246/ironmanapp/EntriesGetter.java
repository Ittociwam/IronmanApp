package cs246.ironmanapp;

/**
 * Created by John on 6/10/15.
 */
public class EntriesGetter extends ServiceCaller {


        double swim = 1.2;
        double bike = 112;
        double run  = 1;

        double swimP;
        double bikeP;
        double runP;

    public void run() {
        double generalPercent =
            (((swim * 46.66) / 336) * 100) +
                    (((bike) / 336) * 100) +
                    (((run * 4.274809) / 336) * 100);

        double swimP = (swim / 2.4) * 100;
        double bikeP = (bike / 112) * 100;
        double runP = (run / 26.2) * 100;

        System.out.println("This is the General percent: " + generalPercent);
        System.out.println("This is the percent for swim: " + swimP);
        System.out.println("		    for bike: " + bikeP);
        System.out.println("	  	    for run: " + runP);


    }

    public double getSwim() {
        return swim;
    }

    public double getBike() {
        return bike;
    }

    public double getRun() {
        return run;
    }


    public double getSwimP() {
        return swimP;
    }

    public double getBikeP() {
        return bikeP;
    }

    public double getRunP() {
        return runP;
    }
}
