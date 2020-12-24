package com.company.day4;

public class Height {
        private final String unit;
        private final int unitValue;
        public Height(String unit, int unitValue) {
            this.unit = unit;
            this.unitValue = unitValue;
        }

        public String getUnit() {
            return unit;
        }

        public int getUnitValue() {
            return unitValue;
        }
}
