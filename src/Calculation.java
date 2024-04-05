public class Calculation {

    // code here
    private final double quantity; // 사용 전력량

    private double basic; // 기본 요금
    private double wattage; // 전력량 요금
    private static final double[] quantityByWattage = {200.0, 200.0}; // 전력량 구간 별 사용 전력량
    private static final double[] amtByWattage = {120.0, 214.6, 307.3}; // 전력량 구간 별 적용 요금
    private double climate; // 기후 환경 요금

    private double meter; // 전기요금계
    private double gst; // 부가가치세
    private double development; // 전력 기반 기금

    private double billingAmt; // 청구 금액

    // 외부에서 new 키워드를 사용해 생성자를 호출할 수 없게 접근 제어자를 private으로 변경
    private Calculation(double quantity) {
        this.quantity = quantity;

        setBasic();
        setWattage();
        setClimate();
        setMeter();
        setGst();
        setDevelopment();
        setBillingAmt();
    }

    private static Calculation instance;

    // Calculation 인스턴스가 생성되지 않은 경우 (null)일 때 한번만 생성
    // 그 외는 기존에 생성된 객체만 리턴함
    public static Calculation getInstance(double quantity) {
        if (instance == null) {
            instance = new Calculation(quantity);
        }

        return instance;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getBasic() {
        return basic;
    }

    // 기본 요금 구하기
    public void setBasic() {
        double basic;

        // 200kWh이하 사용
        if (quantity <= quantityByWattage[0])
            basic = 910;
            // 201~400kWh 사용
        else if (quantity <= quantityByWattage[0] + quantityByWattage[1])
            basic = 1600;
            // 400kWh 초과 사용
        else
            basic = 7300;

        this.basic = basic;
    }

    public double getWattage() {
        return wattage;
    }

    // 전력량 요금 구하기
    public void setWattage() {
        double wattage = .0;

        // 1단계: 처음 200kWh 까지
        if (quantity <= quantityByWattage[0]) {
            wattage += quantity * amtByWattage[0];
            // 2단계: 다음 200kWh 까지
        } else if (quantity <= quantityByWattage[0] + quantityByWattage[1]) { // 200 < quantity <= 400
            wattage += quantityByWattage[0] * amtByWattage[0];
            wattage += (quantity - quantityByWattage[0]) * amtByWattage[1];
            // 3단계: 400kWh 초과
        } else { // 400 < quantity
            wattage += quantityByWattage[0] * amtByWattage[0];
            wattage += quantityByWattage[1] * amtByWattage[1];
            wattage += (quantity - (quantityByWattage[0] + quantityByWattage[1])) * amtByWattage[2];
        }

        this.wattage = wattage;
    }

    public double getClimate() {
        return climate;
    }

    // 기후 환경 요금 구하기
    public void setClimate() {
        this.climate = quantity * 9;
    }

    public double getMeter() {
        return meter;
    }

    // 전기 요금계 구하기
    public void setMeter() {
        this.meter = getBasic() + getWattage() + getClimate();
    }

    public double getGst() {
        return gst;
    }

    // 부가 가치세 구하기
    public void setGst() {
        // 전기 요금계의 10% 후 원 미만 반올림
        this.gst = Math.round(getMeter() * 10.0 / 100.0);
    }

    public double getDevelopment() {
        return development;
    }

    // 전력 기반 기금 구하기
    public void setDevelopment() {
        // 전기 요금계의 3.7% 후 (10원 미만 절사)
        this.development = ((int) (getMeter() * 3.7 / 100.0)) / 10 * 10;
    }

    public double getBillingAmt() {
        return billingAmt;
    }

    // 청구 금액 구하기 (10원 미만 절사)
    public void setBillingAmt() {
        this.billingAmt = ((int) (getMeter() + getGst() + getDevelopment())) / 10 * 10;
    }

    @Override
    public String toString() {
        return String.format("기본요금 : %,.0f 원\n" +
                "전력량요금 : %,.0f 원\n" +
                "기후환경요금 : %,.0f 원\n" +
                "전기요금계 : %,.0f 원\n" +
                "부가가치세 : %,.0f 원 (원미만 반올림)\n" +
                "전력기반기금 : %,.0f 원 (10원미만절사)\n" +
                "청구금액 : %,.0f 원 (10원미만절사)", getBasic(), getWattage(), getClimate(), getMeter(), getGst(), getDevelopment(), getBillingAmt());
    }
}
