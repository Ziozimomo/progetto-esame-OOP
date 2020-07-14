package esameOOP.project.Filters;

import java.util.Calendar;

import esameOOP.project.Model.Post;

/**
 * La classe controlla se la data risulta essere antecedente alla data passata come filtro
 * estende la classe DateFilter.
 * @author Simone Salvoni
 * @author Daniele Staffolani
 */
public class BeforeDateFilter extends DateFilter {
	private Calendar date;
	
	public BeforeDateFilter(String field, String operator, Calendar date) {
		super(field, operator);
		DateFilter.operator = operator;
		this.date = date;
		
	}

	/**
	 * Controlla se la data del post risulta essere antecedente alla data del filtro.
	 * @param post contenente la data da esaminare
	 * @return booleano true se la data risulta essere antecedente del filtro,
	 * false se non lo �
	 */
	
	@Override
	public boolean checkFilter(Post post) {
		int y1, y2, m1, m2, d1, d2;
		y1 = post.getCreated_time().get(Calendar.YEAR);
		y2 = date.get(Calendar.YEAR);
		m1 = post.getCreated_time().get(Calendar.MONTH) + 1;
		m2 = date.get(Calendar.MONTH) + 1;
		d1 = post.getCreated_time().get(Calendar.DAY_OF_MONTH);
		d2 = date.get(Calendar.DAY_OF_MONTH);
		if ((y1 < y2) || ((y1 == y2) && (m1 < m2)) || ((y1 == y2) && (m1 == m2) && (d1 <= d2)))
			return true;
		return false;
	}
}
