package fundamentalista;

public class FundamentoBusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	public FundamentoBusinessException() {
	}

	public FundamentoBusinessException(String msg) {
		super(msg);
	}

	public FundamentoBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FundamentoBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public FundamentoBusinessException(Throwable cause) {
		super(cause);
	}
}
