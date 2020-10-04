package senscript;

import device.SensorNode;
import simulation.WisenSimulation;


public class Command_RUTE extends Command{

	protected SensorNode sensor;
	protected String tabla;
	protected String nodoInicial;
	protected String nodoFinal;
	protected String retorno;
	private int elementos;
	private double[][]pesos;
	private String [][] rutas;
	

	public Command_RUTE(SensorNode sensorNode, String pTabla,String pNodoInicial, String pNodoFinal,String ret) {
		this.sensor= sensorNode;
		this.tabla=pTabla;
		this.nodoInicial=pNodoInicial;
		this.nodoFinal=pNodoFinal;
		this.retorno=ret;
	}

	@Override
	public double execute() {
		String tabName=tabla;
		String var = retorno;


		String x_str = sensor.getScript().getVariableValue(nodoInicial);
		String y_str = sensor.getScript().getVariableValue(nodoFinal);


		int inicio  = Double.valueOf(x_str).intValue();
		int fin  = Double.valueOf(y_str).intValue();

		String[][] tab = sensor.getScript().getTable(tabName);

		System.out.println(tab[6][38]);
		System.out.println(tab[38][6]);
		System.out.println(tab[6][15]);
		System.out.println(tab[15][6]);
		elementos= tab[0].length;

		
		rutas = new String[elementos][elementos];
		pesos = new double[elementos][elementos];



		for(int i=0;i<elementos;i++){
			for(int j=0;j<elementos;j++){
				pesos[i][j]=999;

			}
		}
		
		dijkstra(tab, inicio,0,x_str,inicio);

		
		System.out.println(pesos[inicio][fin]);
		String val = rutas[inicio][fin];


		sensor.getScript().putVariable(var, val);

		WisenSimulation.simLog.add("S" + sensor.getId() + " GET TABLE VALUE "+tabName+"["+inicio+"]["+fin+"] -> "+val);
		return 0;
	}

	public void dijkstra(String[][]tab, int pIni, double pPeso,String ruta,int fuente){
		boolean [] checkPasa= new boolean [elementos];
		for(int i=0;i<elementos;i++)
		{
			checkPasa[i]=false;
		}
		checkPasa[fuente]=true;
		for(int i=1;i<elementos;i++){

			if(pIni!=i){
				double val = Double.valueOf(tab[pIni][i]).doubleValue();
				if(i==38 && pIni==6)
				{
					int a =0;
				}
			

				if(val!=999){

					double pesoAnt= pesos[fuente][i];
					
						if(pesoAnt>(pPeso+val)){
							if(checkPasa[i]==false)	{		
							pesos[fuente][i]=pPeso+val;
							rutas[fuente][i]=ruta+"#"+i;
							checkPasa[i]=true;
							
							dijkstra(tab, i, pPeso+val, ruta+"#"+i,fuente);
						}
					}
				}
			}

		}
	}

	public String toString() {
		return "RUTE";
	}


}
